import sys
from cx_Freeze import setup, Executable

company_name = 'Avengers'
product_name = 'MicroIde'

bdist_msi_options = {
    'upgrade_code': '{Banana-rama-30403344939493}',
    'add_to_path': False,
    'initial_target_dir': r'[ProgramFilesFolder]\%s\%s' % (company_name, product_name),
    }

path = sys.path
build_exe_options = {
"path": path,
"icon": "soka.ico"
}

base = None
if sys.platform == "win32":
    base = "Win32GUI"
    
exe = Executable(script='MicroIde.py',
                 base=base,
                 icon='soka.ico'
                )

setup(  name = "MicroIde",
        version = "1.1",
        description = "Powerfull Calculator for all plattforms",
        executables = [exe],
        options = {'bdist_msi': bdist_msi_options})
