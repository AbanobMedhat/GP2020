from PyQt5.QtWidgets import QMainWindow, QApplication, QTextEdit ,QAction,  QWidget , QVBoxLayout, QInputDialog, QGridLayout
from PyQt5 import uic
import sys
import ide


class UI(QMainWindow):
    portNo = ""
    def __init__(self):
        super().__init__()
        self.intUI()

    def intUI(self):
        menu = self.menuBar()
        filemenu = menu.addMenu('File')
        Port = menu.addMenu('Port')
        Port_Action = QAction("Port", self)
        Port_Action.triggered.connect(self.Port)
        Port.addAction(Port_Action)
        Run = menu.addMenu('Run')
#        Syntax = menu.addMenu('Syntax_Check')
#        Syntax_Action = QAction("Check", self)
#        Syntax_Action.triggered.connect(self.Check)
#        Syntax.addAction(Syntax_Action)
        Save_Action = QAction("Save", self)
        Save_Action.triggered.connect(self.save)
        Close_Action = QAction("Close", self)
        Close_Action.triggered.connect(self.close)
        RunAction = QAction("Run", self)
        RunAction.triggered.connect(self.Run)
        Run.addAction(RunAction)
        filemenu.addAction(Save_Action)
        filemenu.addAction(Close_Action)

        ################           Code For AutoSizing           ################
        self.text = QTextEdit()
        self.text2 = QTextEdit()
        grid = QGridLayout()
        grid.setSpacing(10)
        self.text2.setFixedHeight(70)
        self.text2.setReadOnly(True)
        grid.addWidget(self.text, 1, 0)
        grid.addWidget(self.text2, 2, 0)

        self.setGeometry(300, 300, 300, 150)
        self.setWindowTitle('micropython IDE')
        widget = QWidget()
        widget.setLayout(grid)
        self.setCentralWidget(widget)

        self.show()
        ################      end of code           ########################





    ###########################        Start OF the Functions          ##################
    def Run(self):
        mytext = self.text.toPlainText()
        ide.create_file(mytext)
        print(self.portNo)
        ide.upload_file(self.portNo)
        # ide.run_file(self.portNo)
        self.text2.append("success")

 #   def Check(self):
 #       print(1)


    def Port(self):
        Port_Number, Entered = QInputDialog.getText(self, 'Input Dialog', 'PLease_Enter_Your_Port_Number:')
        self.portNo = Port_Number
        self.text2.append('Port is on ' + self.portNo)

    def save(self):
        with open('main.py', 'w') as f:
            mytext = self.text.toPlainText()
            f.write(mytext)
if __name__ == '__main__':
    app = QApplication(sys.argv)
    ex = UI()
    sys.exit(app.exec_())

