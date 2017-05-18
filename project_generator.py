#!/usr/bin/env python
# coding:utf-8

import os
import shutil
import zipfile

try:
    import requests

except ImportError:
    requests = None
    print 'Project Generator depends on the "requests" lib.'


def download_lastest_src():
    print 'Fetching the lastest version from github...'
    r = requests.get('https://github.com/nekocode/Kotlin-Android-Template/releases/latest', allow_redirects=False)
    lastest_tag = r.headers['Location'].split('/')[-1]
    zipfile_name = 'src-%s.zip' % lastest_tag

    if os.path.exists(zipfile_name):
        print 'Already downloaded [%s].' % zipfile_name
        return zipfile_name, lastest_tag

    print 'Downloading the lastest release [%s]...' % zipfile_name
    url = 'https://github.com/nekocode/Kotlin-Android-Template/archive/%s.zip' % lastest_tag
    r = requests.get(url)
    with open(zipfile_name, 'wb') as data:
        data.write(r.content)

    print 'Download finished.'
    return zipfile_name, lastest_tag


def unzip_src_package(zipfile_name):
    print 'Unziping [%s]...' % zipfile_name

    zfile = zipfile.ZipFile(zipfile_name, 'r')
    names = zfile.namelist()
    root_dir = names[0]

    if os.path.exists(root_dir):
        print 'Already unzipped.'
        return root_dir

    for filename in names:
        path = os.path.join('./', filename)

        if path.endswith('/'):
            if not os.path.exists(os.path.dirname(path)):
                os.mkdir(os.path.dirname(path))

        else:
            file(path, 'wb').write(zfile.read(filename))

    print 'Unzip finished.'
    return root_dir


class TextProcesser:
    def __init__(self, file_path):
        self.file_path = file_path
        self.commands = []

    def rm_line_has_text(self, text):
        self.commands.append(('rm_line', text))
        return self

    def replace_all_text(self, src, dst):
        self.commands.append(('replace', src, dst))
        return self

    def replace_header(self, src, dst):
        self.commands.append(('replace_header', src, dst))
        return self

    def remove_comment(self):
        self.commands.append(('rm_comment', None))
        return self

    def recreate(self, text):
        self.commands = []
        self.commands.append(('recreate', text))
        return self

    def finish(self):
        with open(self.file_path, 'r') as src_file, open(self.file_path + '.new', 'w') as new_file:
            for line in src_file.readlines():
                need_write = True
                need_recreate = None
                for cmd in self.commands:
                    if cmd[0] == 'rm_line' and cmd[1] in line:
                        need_write = False
                        break

                    elif cmd[0] == 'rm_comment' and \
                            (line.startswith('/**') or line.startswith(' * ') or line.startswith(' */')):
                        need_write = False
                        break

                    elif cmd[0] == 'recreate':
                        need_recreate = cmd[1]
                        break

                    elif cmd[0] == 'replace':
                        line = line.replace(cmd[1], cmd[2])

                    elif cmd[0] == 'replace_header' and (line.startswith('package') or line.startswith('import')):
                        line = line.replace(cmd[1], cmd[2])

                if need_recreate is not None:
                    new_file.write(need_recreate)
                    break

                if need_write:
                    new_file.write(line)

        shutil.move(self.file_path + '.new', self.file_path)


class ProjectGenerator:
    def __init__(self, template_zip, version):
        self.template_zip = template_zip
        self.version = version

    def generate_project(self, project_name, package_name):
        template_dir = unzip_src_package(self.template_zip)

        if os.path.exists(project_name):
            shutil.rmtree(project_name)
        shutil.move(template_dir, project_name)

        os.chdir(project_name)
        shutil.move('sample', 'app')

        print 'Creating project [%s]...' % project_name
        self.process(project_name, package_name)
        print 'Creat finished.'

    def process(self, project_name, package_name):
        # =================
        #       Root
        # =================
        # build.gradle
        TextProcesser('build.gradle').rm_line_has_text('android-maven').finish()

        # settings.gradle
        TextProcesser('settings.gradle').recreate("include ':app', ':data'").finish()

        # rm unnessary files
        os.remove('README.md')
        os.remove('.gitattributes')
        shutil.rmtree('art')
        if os.path.exists('project_generator.py'):
            os.remove('project_generator.py')

        # =================
        #       app
        # =================
        # build.gradle
        TextProcesser('app/build.gradle') \
            .replace_all_text('cn.nekocode.template', package_name) \
            .finish()

        # build.gradle
        TextProcesser('app/proguard-rules.pro') \
            .replace_all_text('cn.nekocode.template', package_name) \
            .finish()

        # AndroidManifest.xml
        TextProcesser('app/src/main/AndroidManifest.xml') \
            .replace_all_text('cn.nekocode.template', package_name) \
            .finish()

        # strings.xml
        TextProcesser('app/src/main/res/values/strings.xml') \
            .replace_all_text('Kotlin-Android', project_name) \
            .finish()

        # move package
        package_dir_postfix = package_name.replace('.', '/')
        tmp_package_path = 'app/src/main/javaTmp/' + package_dir_postfix + '/'
        old_package_path = 'app/src/main/java/cn/nekocode/template/'
        os.makedirs(tmp_package_path)

        for f in os.listdir(old_package_path):
            shutil.move(old_package_path + f, tmp_package_path)
        shutil.rmtree('app/src/main/java')

        os.renames('app/src/main/javaTmp', 'app/src/main/java')

        new_package_path = 'app/src/main/java/' + package_dir_postfix + '/'

        # src files
        def process_all_src(path):
            for p in os.listdir(path):
                if os.path.isdir(path + p):
                    process_all_src(path + p + '/')

                elif p.endswith('.kt') or p.endswith('.java'):
                    TextProcesser(path + p) \
                        .remove_comment() \
                        .replace_header('cn.nekocode.template', package_name) \
                        .finish()

        process_all_src(new_package_path)

        # =================
        #       data
        # =================
        package_name += '.data'

        # AndroidManifest.xml
        TextProcesser('data/src/main/AndroidManifest.xml') \
            .replace_all_text('cn.nekocode.template.data', package_name) \
            .finish()

        # move package
        package_dir_postfix = package_name.replace('.', '/')
        tmp_package_path = 'data/src/main/javaTmp/' + package_dir_postfix + '/'
        old_package_path = 'data/src/main/java/cn/nekocode/template/data/'
        os.makedirs(tmp_package_path)

        for f in os.listdir(old_package_path):
            shutil.move(old_package_path + f, tmp_package_path)
        shutil.rmtree('data/src/main/java')

        os.renames('data/src/main/javaTmp', 'data/src/main/java')

        new_package_path = 'data/src/main/java/' + package_dir_postfix + '/'

        # move test package
        package_dir_postfix = package_name.replace('.', '/')
        tmp_package_path = 'data/src/test/javaTmp/' + package_dir_postfix + '/'
        old_package_path = 'data/src/test/java/cn/nekocode/template/data/'
        os.makedirs(tmp_package_path)

        for f in os.listdir(old_package_path):
            shutil.move(old_package_path + f, tmp_package_path)
        shutil.rmtree('data/src/test/java')

        os.renames('data/src/test/javaTmp', 'data/src/test/java')

        new_test_package_path = 'data/src/test/java/' + package_dir_postfix + '/'

        # src files
        def process_all_src(path):
            for p in os.listdir(path):
                if os.path.isdir(path + p):
                    process_all_src(path + p + '/')

                elif p.endswith('.kt') or p.endswith('.java'):
                    TextProcesser(path + p) \
                        .remove_comment() \
                        .replace_header('cn.nekocode.template.data', package_name) \
                        .finish()

        process_all_src(new_package_path)
        process_all_src(new_test_package_path)

        return self


def main():
    project_name = raw_input('Input new project name: ')
    package_path = raw_input('Input the full package path (such as com.company.test): ')

    template_zip, version = download_lastest_src()
    factory = ProjectGenerator(template_zip, version)
    factory.generate_project(project_name, package_path)


if __name__ == '__main__' and requests is not None:
    main()
