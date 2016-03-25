package apeha.market;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import java.io.StringReader;
import java.util.Iterator;
import java.util.List;

public class AddItem {

    private Shell shell = null; // @jve:decl-index=0:visual-constraint="26,6"
    private Text textArea = null;
    private Button buttonWindowAdd = null;
    private Button buttonWindowCancel = null;
    private Transmissible transmissible = null;

    public AddItem(Transmissible interfaceConn) {
        this.transmissible = interfaceConn;
        this.createShell();
        this.shell.open();
    }

    /**
     * This method initializes sShell1
     */
    private void createShell() {
        GridData gridData4 = new GridData();
        gridData4.horizontalAlignment = GridData.FILL;
        gridData4.grabExcessHorizontalSpace = true;
        gridData4.verticalAlignment = GridData.FILL;
        GridData gridData3 = new GridData();
        gridData3.horizontalAlignment = GridData.FILL;
        gridData3.grabExcessHorizontalSpace = true;
        gridData3.verticalAlignment = GridData.FILL;
        GridLayout gridLayout2 = new GridLayout();
        gridLayout2.makeColumnsEqualWidth = true;
        gridLayout2.numColumns = 2;
        GridData gridData1 = new GridData();
        gridData1.horizontalAlignment = GridData.FILL;
        gridData1.grabExcessHorizontalSpace = true;
        gridData1.grabExcessVerticalSpace = true;
        gridData1.horizontalSpan = 2;
        gridData1.verticalAlignment = GridData.FILL;
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        gridLayout.makeColumnsEqualWidth = true;
        shell = new Shell(SWT.SHELL_TRIM | SWT.PRIMARY_MODAL);
        shell.setLayout(gridLayout2);
        shell.setSize(new Point(515, 479));
        shell.setText("Добавить вещи");
        shell.setLayout(gridLayout);
        textArea = new Text(shell, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
        textArea.setLayoutData(gridData1);
        buttonWindowCancel = new Button(shell, SWT.NONE);
        buttonWindowCancel.setText("Отменить");
        buttonWindowCancel.setLayoutData(gridData3);
        buttonWindowAdd = new Button(shell, SWT.NONE);
        buttonWindowAdd.setText("Добавить");
        buttonWindowAdd.setLayoutData(gridData4);
        buttonWindowCancel
                .addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
                    public void widgetSelected(
                            org.eclipse.swt.events.SelectionEvent e) {
                        shell.dispose();
                    }
                });
        buttonWindowAdd
                .addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
                    public void widgetSelected(
                            org.eclipse.swt.events.SelectionEvent e) {
                        List<Item> list = new StreamHandler().fillItemList(
                                new StringReader(textArea.getText()));
                        Iterator<Item> it = list.iterator();
                        List<Item> tableList = transmissible.getList();
                        if (list.size() != 0) {
                            while (it.hasNext()) {
                                tableList.add(it.next());
                            }
                            transmissible.updateTable(tableList);
                        }
                        shell.dispose();
                    }
                });
    }
}
