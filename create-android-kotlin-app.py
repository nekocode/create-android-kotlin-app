#!/usr/bin/env python
# coding:utf-8

import sys
import os
import io
import zipfile
import requests
import re


REPO_URL = 'https://github.com/nekocode/create-android-kotlin-app'
ORIGINAL_PACKAGE_NAME = 'cn.nekocode.caka'
ZIP_UNIX_SYSTEM = 3


class ProjectCreator:
    def __init__(self, archive_data: bytes, project_name: str, package_name: str):
        self.zip_file = zipfile.ZipFile(io.BytesIO(archive_data), 'r')
        self.project_name = project_name
        self.package_name = package_name
        self.root_dir_path = None

    def create(self):
        infolist: [zipfile.ZipInfo] = self.zip_file.infolist()
        self.root_dir_path = infolist[0].filename

        for info in infolist:
            if info.filename.endswith('/'):
                # skip directory
                continue

            last_segment = info.filename.split('/')[-1]
            if last_segment.endswith('.kt'):
                self.kt_file(info)

            elif last_segment.endswith('.gradle.kts'):
                self.gradle_file(info)

            elif last_segment.endswith('.xml'):
                self.xml_file(info)

            elif last_segment.endswith('.py') or last_segment == 'README.md':
                continue

            else:
                self.common_file(info)

    def kt_file(self, info: zipfile.ZipInfo):
        file_name = self.replace_root_path(info.filename)
        file_name = file_name.replace(
            ORIGINAL_PACKAGE_NAME.replace('.', '/'),
            self.package_name.replace('.', '/'))
        content = TextProcessor(self.zip_file.read(info.filename).decode('utf-8'))\
            .replace_all_text(ORIGINAL_PACKAGE_NAME, self.package_name)\
            .remove_unwanted_comments().commit().encode()

        self.write_to_file(file_name, content, info)

    def gradle_file(self, info: zipfile.ZipInfo):
        self.replaceable_file(info)

    def xml_file(self, info: zipfile.ZipInfo):
        self.replaceable_file(info)

    def replaceable_file(self, info: zipfile.ZipInfo):
        file_name = self.replace_root_path(info.filename)
        content = TextProcessor(self.zip_file.read(info.filename).decode('utf-8')) \
            .replace_all_text(ORIGINAL_PACKAGE_NAME, self.package_name) \
            .commit().encode()

        self.write_to_file(file_name, content, info)

    def common_file(self, info: zipfile.ZipInfo):
        file_name = self.replace_root_path(info.filename)
        content = self.zip_file.read(info.filename)

        self.write_to_file(file_name, content, info)

    def replace_root_path(self, file_name: str) -> str:
        return file_name.replace(self.root_dir_path, self.project_name + '/')

    @staticmethod
    def write_to_file(file_name: str, content: bytes, info: zipfile.ZipInfo):
        os.makedirs(os.path.dirname(file_name), exist_ok=True)
        open(file_name, 'wb').write(content)
        if info.create_system == ZIP_UNIX_SYSTEM:
            unix_attributes = info.external_attr >> 16
            if unix_attributes:
                os.chmod(file_name, unix_attributes)


class TextProcessor:
    def __init__(self, txt: str):
        self.txt = txt
        self.commands = []

    def replace_all_text(self, src: str, dst: str) -> 'TextProcessor':
        self.commands.append(('replace_all_text', src, dst))
        return self

    def remove_unwanted_comments(self) -> 'TextProcessor':
        self.commands.append(('remove_unwanted_comments', None))
        return self

    def commit(self) -> str:
        rlt = ''
        for line in self.txt.splitlines():
            skip_line = False

            for cmd in self.commands:
                if cmd[0] == 'replace_all_text':
                    line = line.replace(cmd[1], cmd[2])

                elif cmd[0] == 'remove_unwanted_comments' and (
                        line.startswith('/*') or
                        line.startswith(' *') or
                        line.startswith(' */')):
                    skip_line = True

            if not skip_line:
                if not (len(rlt) == 0 and len(line) == 0):
                    rlt += line + '\n'

        return rlt


def fetch_latest_archive() -> bytes:
    r = requests.get(REPO_URL + '/releases/latest', allow_redirects=False)
    latest_tag = r.headers['location'].split('/')[-1]
    archive_url = REPO_URL + '/archive/%s.zip' % latest_tag
    return requests.get(archive_url).content


def main():
    def green(txt: str) -> str:
        return '\33[32m' + txt + '\33[0m'

    def red(txt: str) -> str:
        return '\33[31m' + txt + '\33[0m'

    if not (len(sys.argv) == 3):
        print('Usage: python3 create-android-kotlin-app.py ' +
              green('<PROJECT_NAME> <APP_PACKAGE_NAME>'))
        return

    project_name = sys.argv[1]
    if os.path.exists(project_name):
        print(red('Error: ') + 'Can not create project. ' +
              'There is already a directory named %s in the current path.' % green(project_name))
        return
    try:
        os.mkdir(project_name)
    except:
        print(red('Error: ') + 'Can not create directory %s' % green(project_name))
        return

    package_name = sys.argv[2]
    if not re.match('^[a-z][a-z0-9_]*(\.[a-z0-9_]+)+[0-9a-z_]$', package_name):
        print(red('Error: ') + 'Invalid java package name %s' % green(package_name))
        return

    print('Creating a new android kotlin app in %s\n' % green("./" + project_name))
    print('Fetching the latest source code archive from %s\nThis might take a couple minutes.' % green(REPO_URL))
    archive_data = fetch_latest_archive()

    print('Unzipping template files...\n')
    ProjectCreator(archive_data, project_name, package_name).create()
    print('Done. Happy hacking!')


if __name__ == '__main__':
    main()

