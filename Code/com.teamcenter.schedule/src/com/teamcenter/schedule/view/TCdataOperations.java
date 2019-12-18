package com.teamcenter.schedule.view;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.teamcenter.rac.aif.kernel.AIFComponentContext;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.kernel.ServiceData;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentSchedule;
import com.teamcenter.rac.kernel.TCComponentScheduleTask;
import com.teamcenter.rac.kernel.TCComponentUser;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;
import com.teamcenter.schemas.soa._2006_03.exceptions.ServiceException;
import com.teamcenter.services.loose.query.SavedQueryService;
import com.teamcenter.services.loose.query._2006_03.SavedQuery.ExecuteSavedQueryResponse;
import com.teamcenter.services.loose.query._2010_04.SavedQuery.FindSavedQueriesCriteriaInput;
import com.teamcenter.services.loose.query._2010_04.SavedQuery.FindSavedQueriesResponse;
import com.teamcenter.services.rac.projectmanagement.ScheduleManagementService;
import com.teamcenter.services.rac.projectmanagement._2007_01.ScheduleManagement;
import com.teamcenter.services.rac.projectmanagement._2012_02.ScheduleManagement.AssignmentCreateContainer;
import com.teamcenter.services.rac.projectmanagement._2012_02.ScheduleManagement.CreatedObjectsContainer;
import com.teamcenter.soa.client.model.ModelObject;
import com.teamcenter.soa.client.model.Property;
import com.teamcenter.schemas.projectmanagement._2011_06.schedulemanagement.SchMgtOptions;
import com.teamcenter.services.rac.projectmanagement._2011_06.ScheduleManagement.SchMgtIntegerOption;
import com.teamcenter.services.rac.projectmanagement._2011_06.ScheduleManagement.ScheduleLoadResponse;
import com.teamcenter.services.rac.projectmanagement._2011_06.ScheduleManagement.LoadScheduleContainer;
import com.teamcenter.services.rac.projectmanagement._2011_06.ScheduleManagement.TaskExecUpdate;


public class TCdataOperations {

	private int Schedule_count;
	private ModelObject[] Schedule_object;
    TCSession session=null;
	public TCdataOperations(TCSession session) {
		this.session=session;
	}
	void getScheduleData() {

		System.out.println("\n inside getScheduleData");
		SavedQueryService service = SavedQueryService.getService(session.getSoaConnection());
		FindSavedQueriesCriteriaInput queryObject[] = new FindSavedQueriesCriteriaInput[1];
		FindSavedQueriesCriteriaInput queryObject1 = new FindSavedQueriesCriteriaInput();
		String[] qryName = { "Schedule_search" };
		String[] qryDesc = { "" };
		queryObject1.queryNames = qryName;
		queryObject1.queryDescs = qryDesc;
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

		String[] id = { "ID" };
		String[] val = { "*" };
		ModelObject save = savequery[0];
		ExecuteSavedQueryResponse result;

		try {
			result = service.executeSavedQuery(save, id, val, 1000);
			setSchedule_count(result.nFound);
			setSchedule_object(result.objects);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	void resourceAssUpdation()
	{
		try {
			
			ScheduleManagementService tt = ScheduleManagementService.getService(session) ;
			TCComponentSchedule mySch = (TCComponentSchedule) getSchedule_object()[0];
			System.out.println("***mySch====" + mySch.toDisplayString());
              
				//TCComponentUser user=new TCComponentUser();
			TCComponentUser user=session.getUser();
			System.out.println("***User====>"+user);	
			Property t = mySch.getPropertyObject("fnd0SummaryTask");
			System.out.println("t is " + t);

			TCComponentScheduleTask mySchTask = (TCComponentScheduleTask) t.getModelObjectValue();
			AIFComponentContext[] SchTaskArr = mySchTask.getChildren();
			System.out.println("SchTaskArr size is " + SchTaskArr.length);
			if(SchTaskArr.length>0)
			{
				for (int j = 2; j < 4; j++) 
				{
					
					TCComponent myTaskActivity=(TCComponent)SchTaskArr[j].getComponent();
					 AssignmentCreateContainer containerObj=new AssignmentCreateContainer();
			           containerObj.task=(TCComponentScheduleTask)myTaskActivity;
			           containerObj.user=user;
			           containerObj.assignedPercent=100;
						
			           AssignmentCreateContainer[] container=new AssignmentCreateContainer[1];
			           container[0]=containerObj;        
						try {
							CreatedObjectsContainer res=tt.assignResources(mySch,container);
							
							int error=res.serviceData.sizeOfPartialErrors();
							System.out.println("Error==="+error);
							if(error==0)
							{
								
							}
							else
						     {
						    	 int n1= res.serviceData.sizeOfPartialErrors();
						    	 String  n2= res.serviceData.toString();
						    	 String[]  n3= res.serviceData.getPartialError(0).getMessages();
						    	 com.teamcenter.rac.util.MessageBox.post("Failed to assign resource or It may be already assigned for "+SchTaskArr[j].toString(),"Alert", MessageBox.INFORMATION); 
						     }
							
							mySch.refresh();
							//return error;
						} catch (Exception e) {
							
							e.printStackTrace();
						}	
				}
			}	
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
		
	void   scheduleCreaion() {
		try {

			
			GregorianCalendar startDate=new GregorianCalendar( 2017, 01, 07 );
	  	   	GregorianCalendar finishDate=new GregorianCalendar( 2018, 01, 07 );
			
	  	  ScheduleManagementService localScheduleManagementService = ScheduleManagementService.getService(session);

		    ScheduleManagement.CreateScheduleContainer newScheduleOperation1 = new ScheduleManagement.CreateScheduleContainer();
	        System.out.println("newScheduleOperation1=="+newScheduleOperation1);
	        newScheduleOperation1.customerName="Prog TDPS";
	        newScheduleOperation1.customerNumber="1222";
	        newScheduleOperation1.datesLinked=false;
	        newScheduleOperation1.description="Progneur Test Schedule";
	        newScheduleOperation1.finishDate=finishDate;
	        newScheduleOperation1.isPublic=false;
	        newScheduleOperation1.isTemplate=false;
	        newScheduleOperation1.linksAllowed=false;
	        newScheduleOperation1.name="Prog_TestSch";
	        newScheduleOperation1.percentLinked=false;
	        newScheduleOperation1.priority=1;
	        newScheduleOperation1.published=false;
	        newScheduleOperation1.startDate=startDate;
	        newScheduleOperation1.status="complete";
		    
	        ScheduleManagement.CreateScheduleContainer[] newScheduleOperation = new ScheduleManagement.CreateScheduleContainer[1];
	        newScheduleOperation[0]=newScheduleOperation1;
	        
	        @SuppressWarnings("deprecation")
	        ServiceData newScheduleOperation111 = localScheduleManagementService.createSchedule(newScheduleOperation);
	       // ReferenceIDContainer createScheduleContainer = ((ScheduleModel)localObject1).newSchedule((ScheduleCreateContainer)localObject2);
	        System.out.println("newScheduleOperation111=="+newScheduleOperation111);
	
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	void   scheduleUpdate() {
		try {
			
	ScheduleManagementService schMngService = ScheduleManagementService.getService(session) ;
			
			TCComponentSchedule mySch = (TCComponentSchedule) getSchedule_object()[0];
			System.out.println("********mySch====" + mySch.toDisplayString());
              
			//TCComponentUser user=new TCComponentUser();
			TCComponentUser user=session.getUser();
			System.out.println("User====>"+user);
			
			Property t = mySch.getPropertyObject("fnd0SummaryTask");
			System.out.println("t is " + t);

			TCComponentScheduleTask mySchTask = (TCComponentScheduleTask) t.getModelObjectValue();
			AIFComponentContext[] SchTaskArr = mySchTask.getChildren();
			System.out.println("ttt size is " + SchTaskArr.length);
			for (int j = 2; j < 4; j++) 
			{
				InterfaceAIFComponent myTask=SchTaskArr[2].getComponent();
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
				Date dateAS = sdf.parse("28-06-2017 17:05");
				Calendar calASDate = Calendar.getInstance();
				
				calASDate.setTime(dateAS);
				
				Date dateAF = sdf.parse("29-06-2017 19:04");
				Calendar calAFDate = Calendar.getInstance();
				calAFDate.setTime(dateAF);
		
				 double newPCVal=0.0;
				 newPCVal=Double.parseDouble("35444");	
				 double d = newPCVal / 1000.0D;
				 double wc=d*Double.parseDouble("7653");
				  
				 ///new for start date and end date
				 
				/*Date startDate = sdf.parse("29-01-2017 17:10");
				Calendar calStartDate =  Calendar.getInstance();				
				calStartDate.setTime(startDate);
				
				Date finishDate = sdf.parse("30-01-2017 41:11");
				Calendar calfinishDate = Calendar.getInstance();				
				calfinishDate.setTime(finishDate);*/
					
				////
				
				 TaskExecUpdate taskupdate=new TaskExecUpdate();
				 taskupdate.task=(TCComponentScheduleTask) myTask;
				 taskupdate.updateAS =true;
				 taskupdate.newAS =calASDate;
				 taskupdate.updateAF =true;
				 taskupdate.newAF =calAFDate;
				 taskupdate.newStatus="in_progress";//(complete/in_progress)
				 taskupdate.newWC =(int) wc; //Work complete
								
	    		 TaskExecUpdate[] taskupdateArr=new TaskExecUpdate[1];
	     		 taskupdateArr[0]=taskupdate;
	     		 	 
				ServiceData schTaskUpdate = schMngService.updateTaskExecution(taskupdateArr);
	    		 if(schTaskUpdate.sizeOfPartialErrors() == 0)
	    		 {
	    			 System.out.println(" Task is updated " );
	    			 
	    		 }else
	    		 {
	    			 System.out.println(" Task is not updated " );
	    		 }
			}
			
			//Example for schedule update
			/*ObjectUpdateContainer[] obj=new ObjectUpdateContainer[1];
			obj[0]=new ObjectUpdateContainer();
			obj[0].object=mySch;
			
			AttributeUpdateContainer[] attributeObj=new AttributeUpdateContainer[1] ;
			attributeObj[0]=new AttributeUpdateContainer();
			attributeObj[0].attrName="object_desc";
			attributeObj[0].attrValue="My test schedule";
			obj[0].updates=attributeObj;
			com.teamcenter.rac.kernel.ServiceData res=tt.updateSchedules(obj);*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public int getSchedule_count() {
		return Schedule_count;
	}
	public void setSchedule_count(int schedule_count) {
		Schedule_count = schedule_count;
	}
	public ModelObject[] getSchedule_object() {
		return Schedule_object;
	}
	public void setSchedule_object(ModelObject[] schedule_object) {
		Schedule_object = schedule_object;
	}
}
