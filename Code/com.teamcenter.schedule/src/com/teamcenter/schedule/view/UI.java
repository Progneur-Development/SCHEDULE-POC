package com.teamcenter.schedule.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class UI {

	public static void main(String[] args) {


		// TODO Auto-generated method stub
		Display display = new Display ();
		Shell schshell = new Shell(display);
		schshell.setSize(645, 381);
		schshell.setText("Schedule Manager Report");
		
	//	schshell.pack();
		schshell.setLayout(new GridLayout(1, false));
		
				
		Composite composite = new Composite(schshell, SWT.NONE);
		GridLayout gl_composite = new GridLayout(6, false);
		composite.setLayout(gl_composite);
		GridData gd_composite = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_composite.heightHint = 71;
		gd_composite.widthHint = 387;
		composite.setLayoutData(gd_composite);
		composite.setBounds(0, 0, 64, 64);
		
		Text name_txt = new Text(composite, SWT.NONE);
		name_txt.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		GridData gd_name_txt = new GridData(SWT.LEFT, SWT.CENTER, false, true, 1, 1);
		gd_name_txt.heightHint = 20;
		gd_name_txt.widthHint = 63;
		name_txt.setLayoutData(gd_name_txt);
		name_txt.setText("Name : ");
		
		
		
		
		final Combo name_combo = new Combo(composite, SWT.NONE);
		GridData gd_name_combo = new GridData(SWT.LEFT, SWT.FILL, false, false, 2, 1);
		gd_name_combo.heightHint = 20;
		gd_name_combo.widthHint = 146;
		name_combo.setLayoutData(gd_name_combo);
		
		
		
		
		
		
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Text txtFrom = new Text(composite, SWT.NONE);
		txtFrom.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		txtFrom.setText("From :");
		GridData gd_txtFrom = new GridData(SWT.LEFT, SWT.CENTER, false, true, 1, 1);
		gd_txtFrom.widthHint = 63;
		gd_txtFrom.heightHint = 20;
		txtFrom.setLayoutData(gd_txtFrom);
		
		final DateTime from_date = new DateTime(composite, SWT.BORDER);
		GridData gd_frdate = new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 1);
		gd_frdate.widthHint = 92;
		gd_frdate.heightHint = 20;
		from_date.setLayoutData(gd_frdate);
		
		
		
		
		
		Text txtTo = new Text(composite, SWT.NONE);
		txtTo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		txtTo.setText("    To :");
		GridData gd_txtTo = new GridData(SWT.CENTER, SWT.CENTER, false, true, 1, 1);
		gd_txtTo.widthHint = 51;
		gd_txtTo.heightHint = 20;
		txtTo.setLayoutData(gd_txtTo);
		
		final DateTime to_date = new DateTime(composite, SWT.BORDER);
		GridData gd_to_date = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1);
		gd_to_date.widthHint = 92;
		gd_to_date.heightHint = 20;
		to_date.setLayoutData(gd_to_date);
		new Label(composite, SWT.NONE);
		
		Button buttonOK = new Button(composite, SWT.NONE);
		GridData gd_button = new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 1);
		gd_button.widthHint = 69;
		gd_button.heightHint = 20;
		buttonOK.setLayoutData(gd_button);
		buttonOK.setText(" OK ");
		buttonOK.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				String userName=name_combo.getItem(name_combo.getSelectionIndex());
				
				//From date
				int day = from_date.getDay();
			    int month = from_date.getMonth() + 1;
			    int year = from_date.getYear();

			    String startDate = (day < 10) ? "0" + day + "/" : day + "/";
			    startDate += (month < 10) ? "0" + month + "/" : month + "/";
			    startDate += year;
			    
			    //To date
			    int day1 = to_date.getDay();
			    int month1 = to_date.getMonth() + 1;
			    int year1 = to_date.getYear();

			    String toDate = (day1 < 10) ? "0" + day1 + "/" : day1 + "/";
			    toDate += (month1 < 10) ? "0" + month1 + "/" : month1 + "/";
			    toDate += year1;
			    
			  //Display table data
					//displayReport(userName,startDate,toDate);
				
			}
		});
	
		Composite composite1_table = new Composite(schshell, SWT.NONE);
		composite1_table.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		composite1_table.setLayout(new GridLayout(1, false));
		GridData gd_composite1_table = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_composite1_table.heightHint = 136;
		gd_composite1_table.widthHint = 387;
		composite1_table.setLayoutData(gd_composite1_table);
		composite1_table.setBounds(0, 0, 64, 64);
		

		// TODO Auto-generated method stub
		 Table ReportTable = new Table(composite1_table, SWT.BORDER | SWT.FULL_SELECTION);
			GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
			gd_table.heightHint = 180;
			ReportTable.setLayoutData(gd_table);
			ReportTable.setHeaderVisible(true);
			ReportTable.setLinesVisible(true);
			
			TableColumn tblclmnNewColumn = new TableColumn(ReportTable, SWT.NONE);
			tblclmnNewColumn.setWidth(100);
			tblclmnNewColumn.setText("Schedule Name");
			
			TableColumn tblclmnNewColumn_1 = new TableColumn(ReportTable, SWT.NONE);
			tblclmnNewColumn_1.setWidth(100);
			tblclmnNewColumn_1.setText("Status");
			
			TableColumn tblclmnNewColumn_2 = new TableColumn(ReportTable, SWT.NONE);
			tblclmnNewColumn_2.setWidth(100);
			tblclmnNewColumn_2.setText("Start Date");
			
			TableColumn tblclmnNewColumn_3 = new TableColumn(ReportTable, SWT.NONE);
			tblclmnNewColumn_3.setWidth(100);
			tblclmnNewColumn_3.setText("End Date");
			
			TableColumn tblclmnNewColumn_4 = new TableColumn(ReportTable, SWT.NONE);
			tblclmnNewColumn_4.setWidth(100);
			tblclmnNewColumn_4.setText("Percentage");
			
			TableColumn tblclmnNewColumn_5 = new TableColumn(ReportTable, SWT.NONE);
			tblclmnNewColumn_5.setWidth(100);
			tblclmnNewColumn_5.setText("User");
			
	
		
		Composite ExReport_composite_1 = new Composite(schshell, SWT.NONE);
		ExReport_composite_1.setLayout(new GridLayout(1, false));
		GridData gd_ExReport_composite_1 = new GridData(SWT.RIGHT, SWT.BOTTOM, true, false, 1, 1);
		gd_ExReport_composite_1.heightHint = 35;
		ExReport_composite_1.setLayoutData(gd_ExReport_composite_1);
		
		Button btnNewButton = new Button(ExReport_composite_1, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnNewButton.setBounds(0, 0, 75, 25);
		btnNewButton.setText("Export to excel");
		
		new Label(ExReport_composite_1, SWT.NONE);
		new Label(ExReport_composite_1, SWT.NONE);
		
		schshell.open ();
		while (!schshell.isDisposed ()) {
			//if (!display.readAndDispatch ()) display.sleep ();
		schshell.close();	
			}
		//display.dispose ();
		
	// TODO Auto-generated method stub

	}

}
