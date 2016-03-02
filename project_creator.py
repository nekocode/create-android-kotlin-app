#!/usr/bin/env python
# coding:utf-8

import os
import sys
import shutil

try:
    import requests
    import zipfile

except ImportError:
    requests = None
    zipfile = None
    print 'Project Creator depends on both the "requests" and "zipfile" libs.'


def download_lastest_src():
    print 'Fetching the lastest version from github...'
    r = requests.get('https://github.com/nekocode/kotgo/releases/latest', allow_redirects=False)
    lastest_tag = r.headers['Location'].split('/')[-1]
    zipfile_name = 'src-%s.zip' % lastest_tag

    if os.path.exists(zipfile_name):
        print 'Already downloaded [%s].' % zipfile_name
        return zipfile_name

    print 'Downloading the lastest release [%s]...' % zipfile_name
    url = 'https://github.com/nekocode/kotgo/archive/%s.zip' % lastest_tag
    r = requests.get(url)
    with open(zipfile_name, 'wb') as data:
        data.write(r.content)

    print 'Download finished.'
    return zipfile_name


def unzip_src_package(zipfile_name):
    print '\nUnziping [%s]...' % zipfile_name

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
        self.file = open(file_path, 'r')
        self.new_file = open(file_path + '.new', 'w')
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

    def end_util(self, text):
        self.commands.append(('end_util', text))
        return self

    def finish(self):
        for line in self.file.readlines():
            write = True
            end = False
            for cmd in self.commands:
                if cmd[0] == 'rm_line' and cmd[1] in line:
                    write = False
                    break

                elif cmd[0] == 'rm_comment' and \
                        (line.startswith('/**') or line.startswith(' * ') or line.startswith(' */')):
                    write = False
                    break

                elif cmd[0] == 'end_util' and line.startswith(cmd[1]):
                    end = True
                    break

                elif cmd[0] == 'replace':
                    line = line.replace(cmd[1], cmd[2])

                elif cmd[0] == 'replace_header' and (line.startswith('package') or line.startswith('import')):
                    line = line.replace(cmd[1], cmd[2])

            if end:
                break

            if write:
                self.new_file.write(line)

        shutil.move(self.file_path + '.new', self.file_path)


class ProjectFactory:
    def __init__(self, template_zip):
        self.template_zip = template_zip

    def create_project(self, project_name, package_name):
        template_dir = unzip_src_package(self.template_zip)

        if os.path.exists(project_name):
            shutil.rmtree(project_name)
        shutil.move(template_dir, project_name)

        os.chdir(project_name)
        shutil.move('sample', 'app')

        print '\nCreating project [%s]...' % project_name
        self.process(project_name, package_name)
        print 'Creat finished.'

    def process(self, project_name, package_name):
        # =================
        #       Root
        # =================
        # build.gradle
        TextProcesser('build.gradle').rm_line_has_text('android-maven').finish()

        # settings.gradle
        TextProcesser('settings.gradle').replace_all_text('sample', 'app').finish()

        # rm readme
        os.remove('README.md')
        os.remove('README_CN.md')
        shutil.rmtree('art')

        # =================
        #       app
        # =================
        # build.gradle
        TextProcesser('app/build.gradle')\
            .rm_line_has_text('com.github.nekocode')\
            .replace_all_text('cn.nekocode.baseframework.sample', package_name)\
            .finish()

        # AndroidManifest.xml
        TextProcesser('app/src/main/AndroidManifest.xml')\
            .replace_all_text('cn.nekocode.baseframework.sample', package_name)\
            .finish()

        # strings.xml
        TextProcesser('app/src/main/res/values/strings.xml')\
            .replace_all_text('BaseFramework', project_name)\
            .finish()

        # move package
        package_dir_postfix = package_name.replace('.', '/')
        tmp_package_path = 'app/src/main/javaTmp/' + package_dir_postfix + '/'
        old_package_path = 'app/src/main/java/cn/nekocode/baseframework/sample/'
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
                    TextProcesser(path + p)\
                        .remove_comment()\
                        .replace_header('cn.nekocode.baseframework.sample', package_name)\
                        .finish()

        process_all_src(new_package_path)

        # =================
        #       data
        # =================
        package_name += '.data'

        # AndroidManifest.xml
        TextProcesser('data/src/main/AndroidManifest.xml')\
            .replace_all_text('cn.nekocode.baseframework.sample.data', package_name)\
            .finish()

        # move package
        package_dir_postfix = package_name.replace('.', '/')
        tmp_package_path = 'data/src/main/javaTmp/' + package_dir_postfix + '/'
        old_package_path = 'data/src/main/java/cn/nekocode/baseframework/sample/data/'
        os.makedirs(tmp_package_path)

        for f in os.listdir(old_package_path):
            shutil.move(old_package_path + f, tmp_package_path)
        shutil.rmtree('data/src/main/java')

        os.renames('data/src/main/javaTmp', 'data/src/main/java')

        new_package_path = 'data/src/main/java/' + package_dir_postfix + '/'

        # src files
        def process_all_src(path):
            for p in os.listdir(path):
                if os.path.isdir(path + p):
                    process_all_src(path + p + '/')

                elif p.endswith('.kt') or p.endswith('.java'):
                    TextProcesser(path + p)\
                        .remove_comment()\
                        .replace_header('cn.nekocode.baseframework.sample.data', package_name)\
                        .finish()

        process_all_src(new_package_path)

        # =================
        #     component
        # =================
        # build.gradle
        TextProcesser('component/build.gradle')\
            .end_util('task sourcesJar')\
            .rm_line_has_text('android-maven')\
            .rm_line_has_text('group=')\
            .finish()

        return self


def main():
    if len(sys.argv) != 3:
        print 'Usage:   ./project_creator.py <projectName> <packagePath>'
        print 'Example: ./project_creator.py NewProject com.test.newproject'

    else:
        project_name = sys.argv[1]
        package_path = sys.argv[2]
        template_zip = download_lastest_src()
        factory = ProjectFactory(template_zip)
        factory.create_project(project_name, package_path)


if __name__ == '__main__' and requests is not None:
    main()



