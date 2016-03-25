package apeha.market;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MarketCounterGUI implements Transmissible {

    List<Item> shellList = new ArrayList<Item>();
    private Shell sShell = null;
    private Table table = null;
    private Button button = null;
    private Button button3 = null;
    private TableColumn nameColum = null;
    private TableColumn priceColum = null;
    private Label label = null;
    private Label label1 = null;
    private Transmissible interfaceConn = null;

    public static void main(String[] args) {
        Display display = Display.getDefault();
        MarketCounterGUI thisClass = new MarketCounterGUI();
        thisClass.createShell();
        thisClass.sShell.open();

        while (!thisClass.sShell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        display.dispose();
    }

    private void createShell() {
        GridData gridData12 = new GridData();
        gridData12.horizontalAlignment = GridData.FILL;
        gridData12.grabExcessHorizontalSpace = true;
        gridData12.grabExcessVerticalSpace = true;
        gridData12.verticalAlignment = GridData.FILL;
        GridData gridData11 = new GridData();
        gridData11.horizontalSpan = 2;
        gridData11.verticalAlignment = GridData.FILL;
        gridData11.horizontalAlignment = GridData.FILL;
        GridData gridData4 = new GridData();
        gridData4.horizontalAlignment = GridData.FILL;
        gridData4.verticalAlignment = GridData.FILL;
        GridData gridData1 = new GridData();
        gridData1.horizontalAlignment = GridData.FILL;
        gridData1.grabExcessVerticalSpace = false;
        gridData1.verticalAlignment = GridData.FILL;
        GridData gridData = new GridData();
        gridData.horizontalAlignment = GridData.FILL;
        gridData.grabExcessHorizontalSpace = true;
        gridData.verticalSpan = 3;
        gridData.grabExcessVerticalSpace = true;
        gridData.verticalAlignment = GridData.FILL;
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        gridLayout.makeColumnsEqualWidth = false;
        sShell = new Shell();
        sShell.setText("Подсчет вещей в лавке на рынке");
        sShell.setLayout(gridLayout);
        sShell.setSize(new Point(807, 432));
        table = new Table(sShell, SWT.NONE);
        table.setHeaderVisible(true);
        table.setLayoutData(gridData);
        table.setLinesVisible(true);
        table.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                final TableEditor editor = new TableEditor(table);
                editor.horizontalAlignment = SWT.LEFT;
                editor.grabHorizontal = true;
                table.addListener(SWT.MouseDown, new Listener() {
                    public void handleEvent(Event event) {
                        Rectangle clientArea = table.getClientArea();
                        Point pt = new Point(event.x, event.y);
                        int index = table.getTopIndex();
                        while (index < table.getItemCount()) {
                            boolean visible = false;
                            final TableItem item = table.getItem(index);
                            for (int i = 0; i < table.getColumnCount(); i++) {
                                Rectangle rect = item.getBounds(i);
                                if (rect.contains(pt)) {
                                    final int column = i;
                                    final Text text = new Text(table, SWT.NONE);
                                    Listener textListener = new Listener() {
                                        public void handleEvent(final Event e) {
                                            switch (e.type) {
                                                case SWT.FocusOut:
                                                    item.setText(column, text.getText());
                                                    text.dispose();
                                                    break;
                                                case SWT.Traverse:
                                                    switch (e.detail) {
                                                        case SWT.TRAVERSE_RETURN:
                                                            item
                                                                    .setText(column, text
                                                                            .getText());
                                                            // FALL THROUGH
                                                        case SWT.TRAVERSE_ESCAPE:
                                                            text.dispose();
                                                            e.doit = false;
                                                    }
                                                    break;
                                            }
                                        }
                                    };
                                    text.addListener(SWT.FocusOut, textListener);
                                    text.addListener(SWT.Traverse, textListener);
                                    editor.setEditor(text, item, i);
                                    text.setText(item.getText(i));
                                    text.selectAll();
                                    text.setFocus();
                                    return;
                                }
                                if (!visible && rect.intersects(clientArea)) {
                                    visible = true;
                                }
                            }
                            if (!visible)
                                return;
                            index++;
                        }
                    }
                });
                if (shellList.size() != 0) {
                    Item item = shellList.get(table.getSelectionIndex());
                    label1.setText("Информация  о предмете:\n" + item.getName() + "\n" + item.getInfo());
                }
            }
        });
        nameColum = new TableColumn(table, SWT.LEFT);
        nameColum.setText("Название предмета");
        nameColum.setWidth(300);
        priceColum = new TableColumn(table, SWT.LEFT);
        priceColum.setText("Цена");
        priceColum.setWidth(100);
        button = new Button(sShell, SWT.NONE);
        button.setText("Добавить вещи");
        button.setLayoutData(gridData1);
        label1 = new Label(sShell, SWT.NONE);
        label1.setText("Информация  о предмете:");
        label1.setLayoutData(gridData12);
        button3 = new Button(sShell, SWT.NONE);
        button3.setText("Очистить всё");
        button3.setLayoutData(gridData4);
        button3.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                table.removeAll();
                shellList.clear();
                label.setText("Всего вещей:");
                label1.setText("Информация  о предмете:");
            }
        });
        button.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                new Thread(new Runnable() {
                    public void run() {
                        Display.getDefault().asyncExec(new Runnable() {
                            public void run() {
                                interfaceConn = getTransmissible();
                                new AddItem(interfaceConn);
                            }
                        });
                    }
                }).start();
            }
        });
        label = new Label(sShell, SWT.NONE);
        label.setText("Всего вещей:");
        label.setLayoutData(gridData11);
    }

    private Transmissible getTransmissible() {
        return this;
    }

    private void fillTable(List<Item> list2) {
        table.removeAll();
        BigDecimal profit = new BigDecimal(0);
        BigDecimal profitBlues = new BigDecimal(0);
        if (list2 != null) {
            Iterator<Item> it = list2.iterator();
            final BigDecimal coeff = new BigDecimal("0.90");
            while (it.hasNext()) {
                Item item = it.next();
                new TableItem(table, SWT.NONE).setText(new String[]{item.getName().replaceAll("(\\(мод\\.\\))|(\\ \\(закл\\.\\))", ""), item.getPrice().toString() + " + " + item.getPriceBlue().toString()});
                profit = profit.add(item.getPrice().multiply(coeff).setScale(2, BigDecimal.ROUND_DOWN));
                profitBlues = profitBlues.add(item.getPriceBlue().multiply(coeff).setScale(2, BigDecimal.ROUND_DOWN));
            }
            label.setText("Всего вещей: " + list2.size() + "\tПрибыль после продажи: " + profit.toString() + " + " + profitBlues.toString());
        }

    }

    @Override
    public List<Item> getList() {
        return this.shellList;
    }

    @Override
    public void updateTable(List<Item> list) {
        fillTable(list);
    }
}
