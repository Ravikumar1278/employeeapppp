/*Title : Employee Management System
 * Author: Jeeva.M
 * Created at 06/10/2021
 * Updated at 30/03/2022
 * Reviewed by  Akshaya , Jaya , Anitha
 */ 
package employeeApplication;
import java.util.Scanner;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.text.ParseException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class Employee {
	private String employeeID;
	private String employeeName;
	private String employeeEmail;
	private String employeeDob;
	private String employeeDoj;
	
	public String getEmployeeID() {
		return employeeID;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public String getEmployeeEmail() {
		return employeeEmail;
	}
	public String getEmployeeDob() {
		return employeeDob;
	}
	public String getEmployeeDoj() {
		return employeeDoj;
	}
}
public class EmployeeDetails extends Employee
{
	EmployeeDetails()
	{
			super();
	}

	Scanner scanner=new Scanner(System.in);
	Scanner datascanner=new Scanner(System.in);
	
	public String employeeId() {
		System.out.println(">>Enter employee ID:");
	    String employeeID=datascanner.nextLine();
	    if(employeeID.startsWith("ACE")&&(employeeID.substring(3).length()==4)) {
	    	return employeeID;
	    }
	    else {
	    	System.out.println("---> No Special Characters Allowed,EmployeeId Should begin with ACE and followed by 4 digits");
	    	
	    }
	    return employeeId();
	}
	public String employeeName() {
		System.out.println(">>Enter Employee Name:");
		String employeeName=datascanner.nextLine();
		Pattern pattern=Pattern.compile("[^a-zA-Z]",Pattern.CASE_INSENSITIVE);
		java.util.regex.Matcher match=pattern.matcher(employeeName);
		boolean check=match.find();
		if(check) {
			System.out.println("-->Employee Name should contain only alphabets do not include special characters or numerics");
		}
		else {
			return employeeName;
		}
		return employeeName();
	}
	public String employeeEmail() {
		System.out.println(">>Enter employee Email:");
		String employeeEmail=datascanner.nextLine();
		Pattern pattern=Pattern.compile("^[A-Za-z0-9]+@+[a-zA-Z]+(.com)$",Pattern.CASE_INSENSITIVE);
		Matcher match=pattern.matcher(employeeEmail);
		boolean check=match.find();
		if(check) {
			return employeeEmail;
		}
		else {
			System.out.println("-->Please enter the valid Email Id, For Example:mail@domain.com \n Domain Name should contain only the Alphabets.");
			}
		return employeeEmail();
	}
	public String employeeDob() {
		System.out.println(">>Enter the employee DOB:");
		String employeeDob=datascanner.nextLine();
		SimpleDateFormat dob=new SimpleDateFormat("dd/MM/yyyy");
		dob.setLenient(false);
		try {
			Date valid=dob.parse(employeeDob);
			long dateCalculation=System.currentTimeMillis()-valid.getTime();
			long age=(long)((long)dateCalculation/(1000.0*60*60*24*365));
			if(age>=18&&age<=60) return employeeDob;
			else {
				System.out.println("--> Please enter the valid date of birth with dd/mm/yyyy format and the age should between 18 to 60");
			}
		 }
		catch(ParseException e) {
			System.out.println("--> Please enter the valid date of birth with dd/mm/yyyy format and the age should between 18 to 60");
			}
		    return employeeDob();
		}
	    public String employeeDoj() {
	    	System.out.println(">>Enter the employee DOJ");
	    	String employeeDoj=datascanner.nextLine();
	    	SimpleDateFormat doj=new SimpleDateFormat("dd/MM/yyyy");
	    	doj.setLenient(false);
	    	try {
	    		Date valid=doj.parse(employeeDoj);
	    		long dateCalculation=System.currentTimeMillis()-valid.getTime();
	    		if(dateCalculation>0) {
	    			return employeeDoj;
	    		}
	    		else {
	    			System.out.println("-->Please enter the valid date of birth with dd/MM/yyyy format and do not have future dates");
	    		}
	    	 }
	    	 catch(ParseException e) {
	    		 System.out.println("-->Please enter the valid date of birth with dd/MM/yyyy format and do not have future dates");
	    	}
	    	return employeeDoj();
	    }
	public void userChoice() 
	{
		ArrayList<Employee> list=new ArrayList<Employee>();
    	int choice;
    	do {
    		System.out.println(">-Welcome to Employee Details-<");
    		System.out.println("1.Add new Employee Details\n2.Display Employee Details\n3.Update Employee Details\n4.Delete Employee Details\n5.Exit");
    		System.out.println("-->Enter your Choice<--");
    		choice=scanner.nextInt();
    		switch(choice) {
    		case 1:{
    			System.out.println("-------------------------------------------------------");
    			System.out.println(">>You have choosen 1.Add new Employee Details");
    			System.out.println("-------------------------------------------------------");
    			
    			String employeeID=employeeId();
    			String employeeName=employeeName();
    			String employeeEmail=employeeEmail();
    			String employeeDob=employeeDob();
    			String employeeDoj=employeeDoj();
    			try {

    				java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/employeeapplication","root","Aspire@123");
    				PreparedStatement preparedStatement=con.prepareStatement("INSERT INTO employeedetails(EmployeeID, EmployeeName, EmployeeEmail, EmployeeDOB, EmployeeDOJ) VALUES(?,?,?,?,?)");
    				preparedStatement.setString(1, employeeID);
    				preparedStatement.setString(2, employeeName); 
    				preparedStatement.setString(3, employeeEmail);
    				preparedStatement.setString(4, employeeDob); 
    				preparedStatement.setString(5, employeeDoj);
    				preparedStatement.executeUpdate();
    				con.close();

    				} 
    			catch(Exception e)
    				{

    				System.out.println(e);
    				}
    			list.add(new Employee());
    			System.out.println("Added Successfully");
    			break;
    		}
    		case 2:
    		{
    			System.out.println("------------------------------------------------");
    			System.out.println(">>You have choosen 2.Display Employee Details");
    			System.out.println("------------------------------------------------");
    			System.out.println("-->Enter the Employee ID for display the details");
    			String employeeID=datascanner.nextLine();
    			
    			try {
    				java.sql.Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/employeeapplication","root","Aspire@123");
    				Statement statement=connection.createStatement();
    				String sql="SELECT EmployeeID, EmployeeName, EmployeeEmail, EmployeeDOB, EmployeeDOJ from employeedetails WHERE EmployeeID='"+employeeID+"'";
    				ResultSet resultSet=statement.executeQuery(sql);
    				if(resultSet.next())
    				{
    					System.out.println(">-----------------<");
    					System.out.println(">>Employee Details");
    					System.out.println(">-----------------<");
    					System.out.println("Employee ID:"+resultSet.getString(1));
    					Thread.sleep(2000);
    					System.out.println("Employee Name:"+resultSet.getString(2));
    					Thread.sleep(2000);
    					System.out.println("Employee Email:"+resultSet.getString(3));
    					Thread.sleep(2000);
    					System.out.println("Employee DOB:"+resultSet.getString(4));
    					Thread.sleep(2000);
    					System.out.println("Employee DOJ:"+resultSet.getString(5));
    					System.out.println(">>----------------<<");
    				}
    				else
    				{
    					System.out.println("-->Record not found");
    				}
    			}
    			catch(Exception exception)
    			{
    				System.out.println("Please chechk your EmployeeId");
    			}
    			break;
    		}
    		case 3:{
    			System.out.println("------------------------------------------------");
    			System.out.println(">>You have choosen 3.Update Employee Details");
    			System.out.println("------------------------------------------------");
    			System.out.println("Enter Employee ID");
    			String employeeID=datascanner.next();
    			System.out.println("Add new employee details");
    			String employeeId=employeeId();
    			String employeeName=employeeName();
    			String employeeEmailId=employeeEmail();
    			String employeeDob=employeeDob();
    			String employeeDoj=employeeDoj();
    			try {
    				
    			java.sql.Connection connection =DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/employeeapplication","root","Aspire@123");
    			PreparedStatement preparedStatement=connection.prepareStatement("UPDATE employeedetails SET EmployeeID=?,EmployeeName=?,EmployeeEmail=?,EmployeeDOB=?,EmployeeDOJ=? where EmployeeID=?");
    			preparedStatement.setString(1, employeeId);
    			preparedStatement.setString(2, employeeName);
    			preparedStatement.setString(3, employeeEmailId);
    			preparedStatement.setString(4, employeeDob);
    			preparedStatement.setString(5, employeeDoj);
    			preparedStatement.setString(6, employeeID);
    			preparedStatement.executeUpdate();
    			System.out.println("Done");
    			connection.close();
    			}
    			catch(Exception exception)
    			{
    			System.out.println(exception);
    			System.out.println("File not found");
    			}
    			break;	
    		}
    		case 4:{
    			System.out.println("--------------------------------------------");
    			System.out.println(">>You have choosen 4.Delete Employee Details");
    			System.out.println("--------------------------------------------");
    			System.out.println("-->Enter the Employee ID for deleting:");
    			String employeeID=datascanner.nextLine();
    			try {
    			java.sql.Connection connection =DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/employeeapplication","root","Aspire@123");
    			PreparedStatement preparedStatement=connection.prepareStatement("DELETE from employeedetails where EmployeeID=?");
    			preparedStatement.setString(1, employeeID); 			
    			preparedStatement.executeUpdate();
    			System.out.println("Employee's Detail is Deleted");
    			connection.close();
    			}
    			catch(Exception exception)
    			{
    			System.out.println(exception);
    			System.out.println("EmployeeID not found");
    			}
    			break;
    		}
    		case 5: {
    			System.out.println("---------------------------");
    			System.out.println(">>You have choosen 5.Exit");
    			System.out.println("---------------------------");
    			System.out.println(">--Thank you for using Aspire employee management system--<");
    			break;
    		}
    		default:
    		{
    			System.out.println("->Kindly choose between the given options<-");
    			break;
    		}
    		
    	}
    }while(choice!=5);
    
  }
	
}