
import sys
import glob
import serial


from PyQt5 import QtCore
from PyQt5.QtWidgets import *
from PyQt5.QtCore import *


def serial_ports():
    """ Lists serial port names

        :raises EnvironmentError:
            On unsupported or unknown platforms
        :returns:
            A list of the serial ports available on the system
    """
    if sys.platform.startswith('win'):
        ports = ['COM%s' % (i + 1) for i in range(256)]
    elif sys.platform.startswith('linux') or sys.platform.startswith('cygwin'):
        # this excludes your current terminal "/dev/tty"
        ports = glob.glob('/dev/tty[A-Za-z]*')
    elif sys.platform.startswith('darwin'):
        ports = glob.glob('/dev/tty.*')
    else:
        raise EnvironmentError('Unsupported platform')

    result = []
    for port in ports:
        try:
            s = serial.Serial(port)
            s.close()
            result.append(port)
        except (OSError, serial.SerialException):
            pass
    return result


mytext = "1"
'''
class Signal(QObject):
    sending = pyqtSignal()

    def __init__(self):
        # Initialize the PunchingBag as a QObject
        QObject.__init__(self)

    def punch(self):
        self.sending.emit()


#    sending = pyqtSignal()

#    def __init__(self):
#        # Initialize the PunchingBag as a QObject
#        QObject.__init__(self)

#    def send(self):
#        self.sending.emit()


class Widget(QWidget):
    def __init__(self):
        super().__init__()
        self.initUI()


    def initUI(self):
        self.btn = QPushButton("name",self)
        self.btn.clicked.connect(self.name)
#        self.b.punch()

        self.b = Signal()
        self.b.sending.connect(self.save)

        self.text = QTextEdit()
        self.text2 = QTextEdit()
        self.treeview = QTreeView()
        path = QDir.rootPath()
        self.dirModel = QFileSystemModel()
        self.dirModel.setRootPath(QDir.rootPath())
        self.dirModel.setFilter(QDir.NoDotAndDotDot | QDir.AllDirs | QDir.Files)
        self.treeview.setModel(self.dirModel)
        self.treeview.setRootIndex(self.dirModel.index(path))

        vbox = QVBoxLayout()
        Left_hbox = QHBoxLayout()
        Right_hbox = QHBoxLayout()

        Left_hbox.addWidget(self.treeview)
        Right_hbox.addWidget(self.text)

        Left_hbox_Layout = QWidget()
        Left_hbox_Layout.setLayout(Left_hbox)

        Right_hbox_Layout = QWidget()
        Right_hbox_Layout.setLayout(Right_hbox)


        H_splitter = QSplitter(Qt.Horizontal)
        H_splitter.addWidget(Left_hbox_Layout)
        H_splitter.addWidget(Right_hbox_Layout)
        H_splitter.setStretchFactor(1, 1)

        V_splitter = QSplitter(Qt.Vertical)
        V_splitter.addWidget(H_splitter)
        V_splitter.addWidget(self.text2)

        Final_Layout = QHBoxLayout(self)
        Final_Layout.addWidget(V_splitter)

        self.setLayout(Final_Layout)
        self.show()

    def name(self):
        self.b.punch()

    @pyqtSlot()
    def save(self):
        global mytext
        mytext = self.text.toPlainText()
        print(1)
        print(mytext)
#        print(1)




class Signals():
    def __init__(self):
        super().__init__()
        self.do_something(str)

    @QtCore.pyqtSlot(str)
    def do_something(self,sin):
        with open('somefile.txt', 'w') as f:
            mytext = sin
            f.write(mytext)
#    @pyqtSlot()
#    def do_something(self,sin):
#        with open('somefile.txt', 'w') as f:
#            mytext = sin.toPlainText()
#            f.write(mytext)

'''

mytext = " "

class Signal(QObject):
    sending = pyqtSignal()
    reading = pyqtSignal(str)
    def __init__(self):
        # Initialize the PunchingBag as a QObject
        QObject.__init__(self)

    def punch(self):
        self.sending.emit()


#    sending = pyqtSignal()

#    def __init__(self):
#        # Initialize the PunchingBag as a QObject
#        QObject.__init__(self)

#    def send(self):
#        self.sending.emit()

#@pyqtSlot()
#def send():
#    print('send')
#text = QTextEdit()

text = QTextEdit

class Widget(QWidget):
    def __init__(self):
        super().__init__()
        self.initUI()


    def initUI(self):
    #    self.btn = QPushButton("name",self)
    #    self.btn.clicked.connect(self.save)
        global text
        text = QTextEdit()

        #self.text = QTextEdit()

        self.text2 = QTextEdit()
        self.treeview = QTreeView()
        path = QDir.rootPath()
        self.dirModel = QFileSystemModel()
        self.dirModel.setRootPath(QDir.rootPath())
        self.dirModel.setFilter(QDir.NoDotAndDotDot | QDir.AllDirs | QDir.Files)
        self.treeview.setModel(self.dirModel)
        self.treeview.setRootIndex(self.dirModel.index(path))

        vbox = QVBoxLayout()
        Left_hbox = QHBoxLayout()
        Right_hbox = QHBoxLayout()

        Left_hbox.addWidget(self.treeview)
        Right_hbox.addWidget(text)

#        Right_hbox.addWidget(self.text)

        Left_hbox_Layout = QWidget()
        Left_hbox_Layout.setLayout(Left_hbox)

        Right_hbox_Layout = QWidget()
        Right_hbox_Layout.setLayout(Right_hbox)


        H_splitter = QSplitter(Qt.Horizontal)
        H_splitter.addWidget(Left_hbox_Layout)
        H_splitter.addWidget(Right_hbox_Layout)
        H_splitter.setStretchFactor(1, 1)

        V_splitter = QSplitter(Qt.Vertical)
        V_splitter.addWidget(H_splitter)
        V_splitter.addWidget(self.text2)

        Final_Layout = QHBoxLayout(self)
        Final_Layout.addWidget(V_splitter)

        self.setLayout(Final_Layout)
    #    self.show()
    @pyqtSlot(str)
    def Saving(s):
#        global mytext
        #global name
        global mytext
        mytext = "yes"
        mytext = text.toPlainText()
#        mytext = self.text.toPlainText()
#        print(mytext)

#        print(1)

 #       textChanged = pyqtSignal(str)
 #       main = Signals()
 #       textChanged = self.text.toPlainText()
 #       self.textChanged.emit(textChanged)
 #       self.textChanged.connect(main.do_something)

        #    s = Signals()
    #    self.signal = pyqtSignal()
    #    self.signal.connect(s.do_something())
    #    self.signal.emit(self.text)
	#	with open('somefile.txt', 'w') as f:
#			mytext = self.text.toPlainText()
#			f.write(mytext)




@pyqtSlot(str)
def reading(s):
    print(s)
    b = Signal()
    b.reading.connect(Widget.Saving)
    b.reading.emit(s)


class UI(QMainWindow):
    def __init__(self):
        super().__init__()
        self.ui = Widget()
        self.intUI()

    def intUI(self):

        self.b = Signal()

        self.b.reading.connect(reading)

        menu = self.menuBar()
        filemenu = menu.addMenu('File')
        Port = menu.addMenu('Port')
        Run = menu.addMenu('Run')

        Port_Action = QMenu('port',self)

        res = serial_ports()

        Action = []
#        Port_Action.findChild()
        for i in range(len(res)):
            s = res[i]

#            Listed_Ports = QAction(s, self)

            Port_Action.addAction(s, self.PortClicked)


#            Port_Action.triggered.connect(self.Port)

#        Listed_Ports = QAction("Ports_We have",self)
#        Port_Action.addAction(Listed_Ports)
        Port.addMenu(Port_Action)

#        Port_Action = QAction("Port", self)
#        Port_Action.triggered.connect(self.Port)
#        Port.addAction(Port_Action)

#        Listed_Ports = QAction("List",self)


        # Making and adding Run Actions
        RunAction = QAction("Run", self)
        RunAction.triggered.connect(self.Run)
        Run.addAction(RunAction)


        # Making and adding File Features
        Save_Action = QAction("Save", self)
        Save_Action.triggered.connect(self.save)
        Save_Action.setShortcut("Ctrl+S")
        Close_Action = QAction("Close", self)
        Close_Action.setShortcut("Alt+c")
        Close_Action.triggered.connect(self.close)

        filemenu.addAction(Save_Action)
        filemenu.addAction(Close_Action)


        #############        Qsplitter        Adding ###########
        #############        Adding Code hebaaaaaaaaaaaaaaaa   ############
        #############        Showing the current directories of the user   ###########
        #############        changing the color and the font ################


        self.setGeometry(300, 300, 300, 150)
        self.setWindowTitle('micropython IDE')
        widget = Widget()
        self.setCentralWidget(widget)
        self.show()


    ###########################        Start OF the Functions          ##################
    def Run(self):
        if self.port_flag == 0:
            self.text2.append("yeeeeeeeeeeeaaaaaaaaaaaaaaaaaah")
        else:
            self.text2.append("Please Select Your Port Number First")

    @QtCore.pyqtSlot()
    def PortClicked(self):
        action = self.sender()
        print(action.text())


#    def Port(self):
#        action = self.sender()
#        print('Action: ', action.data())
#        Port_Number, Entered = QInputDialog.getText(self, 'Input Dialog', 'PLease_Enter_Your_Port_Number:')
#        self.port_flag = 0

    def save(self):
        #print(mytext)
        self.b.reading.emit("name")
        print(mytext)
	######### 3aiz a3ml call ll function ly fe class l widget we a4el ly hena  #################
	######### l mfrod tt3ml b signal and slotting from class to another ###############
	
#        with open('somefile.txt', 'w') as f:
#            mytext = self.ui.text.toPlainText()
#            f.write(mytext)


if __name__ == '__main__':
    app = QApplication(sys.argv)
    ex = UI()
    #ex = Widget()
    sys.exit(app.exec_())









'''
from PyQt5.QtWidgets import QMainWindow, QApplication, QTextEdit ,QAction,  QWidget ,QHBoxLayout, QVBoxLayout, QInputDialog, QGridLayout, QLineEdit,QSplitter
from PyQt5 import uic
from PyQt5.QtCore import Qt
import sys

class UI(QMainWindow):
    def __init__(self):
        super().__init__()
        self.intUI()

    def intUI(self):
        ################           Code For AutoSizing           ################
        self.text = QTextEdit()
        self.text2 = QTextEdit()
 #       self.button_layout = QHBoxLayout()
#        self.button_layout.addStretch()
 #       self.button_layout.addWidget(self.text,stretch=1)
 #       widget2 = QWidget()
  #      widget2.setLayout(self.button_layout)
#        self.button_layout.addStretch()
        grid = QGridLayout()
        grid.setSpacing(10)
        self.text2.setFixedHeight(70)
        self.text2.setReadOnly(True)
        grid.addWidget(self.text, 1, 0)
        grid.addWidget(self.text2, 2, 0)

#        vbox = QVBoxLayout(self)
#        vbox.addWidget(self.text,stretch=0)
        #self.setLayout(vbox)
        self.setGeometry(300, 300, 300, 150)
        self.setWindowTitle('micropython IDE')
        widget = QWidget()
        widget.setLayout(grid)

        splitter = QSplitter(Qt.Horizontal)
        splitter.addWidget(grid)
        splitter.setSizes([200,200])

#        self.setLayout(grid)
        self.setCentralWidget(widget)

        self.show()
        ################      end of code           ########################


if __name__ == '__main__':
    app = QApplication(sys.argv)
    ex = UI()
    sys.exit(app.exec_())
'''
