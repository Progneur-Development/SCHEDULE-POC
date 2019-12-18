package com.teamcenter.schedule.view;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.wb.swt.SWTResourceManager;

import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.schemas.soa._2006_03.exceptions.ServiceException;
import com.teamcenter.services.loose.query.SavedQueryService;
import com.teamcenter.services.loose.query._2006_03.SavedQuery.ExecuteSavedQueryResponse;
import com.teamcenter.services.loose.query._2010_04.SavedQuery.FindSavedQueriesCriteriaInput;
import com.teamcenter.services.loose.query._2010_04.SavedQuery.FindSavedQueriesResponse;
import com.teamcenter.soa.client.model.ModelObject;
import com.teamcenter.soa.exceptions.NotLoadedException;



public class CopyOfResourceAssignee {

	TCSession session=null;
	ModelObject[] Schedule_object = null;
	int Schedule_count = 0;
	String nameComboVal[]=null;
	private Combo name_combo;
	 Vector<String> resVector=null;
	ArrayList<String> resList=null;
	private Table ReportTable;
	
	private Table dummyReportTable;
	public CopyOfResourceAssignee(TCSession session) {
		// TODO Auto-generated constructor stub
		this.session=session;
		getScheduleData();
	}


	public void resource_AssUI()
	{

		// TODO Auto-generated method stub
	//	Display display = new Display ();
		Shell schshell = new Shell();
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
		
		
		try
		{
			//Assign combo values
			getResourceComboData();
					
		}catch(Exception e)
		{
			System.out.println("\n resource data error");
		}
		
		name_combo = new Combo(composite, SWT.NONE);
		GridData gd_name_combo = new GridData(SWT.LEFT, SWT.FILL, false, false, 2, 1);
		gd_name_combo.heightHint = 20;
		gd_name_combo.widthHint = 146;
		name_combo.setLayoutData(gd_name_combo);
		
		//Add data to combo box
		if(resList!=null && resList.size()>0)
		{
			nameComboVal=new String[resList.size()];
			for(int i=0;i<resList.size();i++)
				nameComboVal[i]=resList.get(i);
			
			name_combo.setItems(nameComboVal);
			
		}
		
		
		
		
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
					displayReport(userName,startDate,toDate);
				
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
		
		 ReportTable = new Table(composite1_table, SWT.BORDER | SWT.FULL_SELECTION);
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
		
		//Display table data
		displayReport("*","","");
		
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
	//	while (!schshell.isDisposed ()) {
			//if (!display.readAndDispatch ()) display.sleep ();
			//schshell.close();	
	//		}
		//display.dispose ();
		
	}
	
	//search criteria
	private void searchCriteria(String userName, String startDate,String toDate)
	{
		try 
		{
			if(userName.equals("*"))
			{
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//Assign data to combo box
	void getResourceComboData()
	{
    	 try
		 {
			 resList=new ArrayList<String>();
			 resVector=new Vector<String>();
			 for(int k=0;k<Schedule_object.length;k++)
				{
					try {
						  
						//resList.add(Schedule_object[k].getPropertyDisplayableValue("fnd0Schedulemember_taglist").toString());
						String ele=Schedule_object[k].getPropertyDisplayableValue("fnd0Schedulemember_taglist").toString();
						System.out.println("**ele=="+ele);
						if(ele.contains(","))
						  {
							  String str[]=ele.split(",");
							  for(int j=0;j<str.length;j++)
								  if(str[j]!=null && str[j].trim().length()>0)
								     resVector.add(str[j].trim());
								  
						  }else
						  {
							  System.out.println("ele=="+ele);
							  if(ele!=null && ele.trim().length()>0)
								  resVector.add(ele.trim());
							  
						  }
							  
						
					} catch (NotLoadedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		
			}
			
				
				//Remove duplicates
				for(int i=0;i<resVector.size();i++)
				{
					if(i==0)
						resList.add(resVector.get(i));
					
		            if (!resList.contains(resVector.get(i)))  
		            		resList.add(resVector.get(i)); 
		         
				}	
		 }catch(Exception e)
		 {
			 e.printStackTrace();
		 }
	
	}
	void getScheduleData()
	{
       
		System.out.println("\n inside getScheduleData"); 
		SavedQueryService service = SavedQueryService.getService(session.getSoaConnection());
		FindSavedQueriesCriteriaInput queryObject[] = new FindSavedQueriesCriteriaInput[1];
		FindSavedQueriesCriteriaInput queryObject1 = new FindSavedQueriesCriteriaInput();
		String[] qryName={"Schedule_search"};
		String[] qryDesc={""};
		queryObject1.queryNames=qryName;
		queryObject1.queryDescs=qryDesc;
		FindSavedQueriesResponse response;
		queryObject[0] = queryObject1;
		ModelObject[] savequery = null;
		try {
    	   response = service.findSavedQueries(queryObject);
    	   savequery = response.savedQueries;
    	  
		} catch (ServiceException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		
		String[] id={"ID"};
		String[] val={"*"};
		ModelObject save = savequery[0];
		ExecuteSavedQueryResponse result;
		
		 try
		 {
			 result = service.executeSavedQuery(save, id, val,10);
			 Schedule_count=result.nFound;
			 Schedule_object = result.objects;
			
		 }catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 
	
	}

	private void displayReport(String searcStr,String froDate,String toDate) {
		try {
			System.out.println("inside displayReport method");
			
			LinkedHashMap<String, ArrayList<String>> dataList=getTableData();
			Set<String> set = dataList.keySet();
			if(searcStr.equals("*") || searcStr.equals(""))
			{
				for(String key:set)
				{
					 TableItem item = new TableItem (ReportTable, SWT.NONE);
					ArrayList<String> dd = dataList.get(key);
					 for (int i=0; i<dd.size(); i++) {
						 item.setText (i,dd.get(i));
						 item.setData(key);
					 }
				}
			}else
			{
				//ReportTable.clearAll();
				
				//clear table
				    int indices[]=new int[set.size()];
					TableItem[] items = ReportTable.getItems();
					int cc=0;
					for(int b=0;b<items.length;b++)
					{
						TableItem tabitem=items[b];
						String key = tabitem.getData().toString();
						if(key.contains(searcStr))
						{ 
							for(int a=0;a<ReportTable.getColumnCount();a++)
							 {
								indices[cc]=Integer.parseInt(key.split("-")[0]);

							 }
						}else
						{
							for(int a=0;a<ReportTable.getColumnCount();a++)
							 {
								  tabitem.setText(a,"");
								 							 }
						}
					}
					// ReportTable.remove(indices);
					
				
				/*for(String key:set)
				{
					//convert key data into aaray list
					if(key.contains(searcStr))
					{
						 ArrayList<String> dd = dataList.get(key);
						 TableItem item = new TableItem (ReportTable, SWT.NONE);
						 for (int i=0; i<dd.size(); i++) {
							 item.setText (i,dd.get(i));
							 item.setData(dd.get(i));
						 }
					}
					
				}*/
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	LinkedHashMap<String, ArrayList<String>> getTableData()
	{
		 LinkedHashMap<String, ArrayList<String>> scheduleData=new LinkedHashMap<String, ArrayList<String>>();
    	 try
		 {
    		
			 ArrayList<String> data = new ArrayList<String>();
			 for(int k=0;k<Schedule_object.length;k++)
				{
					try {
						   data = new ArrayList<String>();
						   String sche= Schedule_object[k].toString();
						   String status=Schedule_object[k].getPropertyDisplayableValue("fnd0SSTStatus").toString();
						   String startDate=Schedule_object[k].getPropertyDisplayableValue("start_date").toString();
						   String endDate=Schedule_object[k].getPropertyDisplayableValue("finish_date").toString();
						   String percentage=Schedule_object[k].getPropertyDisplayableValue("fnd0SSTCompletePercent").toString();
						   String members=Schedule_object[k].getPropertyDisplayableValue("fnd0Schedulemember_taglist").toString();
						  
						   if(sche!=null) data.add(sche);
						   else  data.add("");
						   
						   if(status!=null)  data.add(status);
						   else  data.add("");
						   
						   if(startDate!=null) data.add(startDate);
						   else  data.add("");
						   
						   if(endDate!=null) data.add(endDate);
						   else  data.add("");
						   
						   if(percentage!=null) data.add(percentage);
						   else  data.add("");
						   
						   if(members!=null) data.add(members);
						   else  data.add("");
						   scheduleData.put(k+"-,"+members, data);
						   
						} catch (NotLoadedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		
			}
			
		 }catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		return scheduleData;
		 
	
	}
}
