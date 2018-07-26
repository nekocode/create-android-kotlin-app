#!/usr/bin/env python
# coding:utf-8

import sys
import os
import io
import zipfile
import requests
import re


REPO_URL = 'https://github.com/nekocode/create-android-kotlin-app'
ORIGINAL_PACKAGE_NAME = 'cn.nekocode.gank'


class ProjectCreator:
    def __init__(self, archive_data: bytes, project_name: str, package_name: str):
        self.zip_file = zipfile.ZipFile(io.BytesIO(archive_data), 'r')
        self.project_name = project_name
        self.package_name = package_name
        self.root_dir_path = None

    def create(self):
        entry_names = self.zip_file.namelist()
        self.root_dir_path = entry_names[0]

        for entry_name in entry_names:
            if entry_name.endswith('/'):
                # skip directory
                continue

            last_semgnet = entry_name.split('/')[-1]
            if last_semgnet.endswith('.kt'):
                self.kt_file(entry_name)

            elif last_semgnet.endswith('.gradle'):
                self.gradle_file(entry_name)

            elif last_semgnet == 'AndroidManifest.xml':
                self.manifest_file(entry_name)

            elif last_semgnet.endswith('.py') or last_semgnet == 'README.md':
                continue

            else:
                self.common_file(entry_name)

    def kt_file(self, entry_name: str):
        file_name = self.replace_root_path(entry_name)
        file_name = file_name.replace(
            ORIGINAL_PACKAGE_NAME.replace('.', '/'),
            self.package_name.replace('.', '/'))
        content = TextProcesser(self.zip_file.read(entry_name).decode('utf-8'))\
            .replace_all_text(ORIGINAL_PACKAGE_NAME, self.package_name)\
            .remove_unwanted_comments().commit().encode()

        self.write_to_file(file_name, content)

    def gradle_file(self, entry_name: str):
        self.manifest_file(entry_name)

    def manifest_file(self, entry_name: str):
        file_name = self.replace_root_path(entry_name)
        content = TextProcesser(self.zip_file.read(entry_name).decode('utf-8'))\
            .replace_all_text(ORIGINAL_PACKAGE_NAME, self.package_name)\
            .commit().encode()

        self.write_to_file(file_name, content)

    def common_file(self, entry_name: str):
        file_name = self.replace_root_path(entry_name)
        content = self.zip_file.read(entry_name)

        self.write_to_file(file_name, content)

    def replace_root_path(self, entry_name: str) -> str:
        return entry_name.replace(
            self.root_dir_path,
            self.project_name + '/')

    @staticmethod
    def write_to_file(file_name: str, content: bytes):
        os.makedirs(os.path.dirname(file_name), exist_ok=True)
        open(file_name, 'wb').write(content)


class TextProcesser:
    def __init__(self, txt: str):
        self.txt = txt
        self.commands = []

    def replace_all_text(self, src: str, dst: str) -> 'TextProcesser':
        self.commands.append(('replace_all_text', src, dst))
        return self

    def remove_unwanted_comments(self) -> 'TextProcesser':
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


def fetch_lastest_archive() -> bytes:
    r = requests.get(REPO_URL + '/releases/latest', allow_redirects=False)
    lastest_tag = r.headers['location'].split('/')[-1]
    archive_url = REPO_URL + '/archive/%s.zip' % lastest_tag
    return requests.get(archive_url).content


def main():
    if not (len(sys.argv) == 3):
        print("Usage: python3 create-android-kotlin-app.py PROJECT_NAME APP_PACKAGE_NAME")
        return

    project_name = sys.argv[1]
    if os.path.exists(project_name):
        print('Can not create project. There is already a directory named "%s" in the current path.' % project_name)
        return
    try:
        os.mkdir(project_name)
    except:
        print('Can not create directory "%s".' % project_name)
        return

    package_name = sys.argv[2]
    if not re.match('^[a-z][a-z0-9_]*(\.[a-z0-9_]+)+[0-9a-z_]$', package_name):
        print('Invaild java package name "%s".' % package_name)
        return

    print('Creating a new android kotlin app in "%s".\n' % project_name)
    print('Fetching the lastest source code archive from %s\nThis might take a couple minutes.' % REPO_URL)
    archive_data = fetch_lastest_archive()

    print('Unziping template files...\n')
    ProjectCreator(archive_data, project_name, package_name).create()
    print('Done. Happy hacking!')


if __name__ == '__main__':
    main()

