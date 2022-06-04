package Tags;


import javax.servlet.jsp.tagext.*;


import javax.servlet.jsp.*;
import java.io.*;

public class ErrorMessageTag extends SimpleTagSupport{

	private String errorMessage;
	private boolean error;


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public void setError(boolean error) {
		this.error = error;
	}
	
	StringWriter sw = new StringWriter();
	

	public void doTag() throws JspException, IOException {
	      JspWriter out = getJspContext().getOut();
	      
	      

	      if(errorMessage != null && error) {
	    	  
	    	  out.println("<small style='color:red'>" + errorMessage + "</small>");
	    	  
	      }else {
	    	  
	    	  getJspBody().invoke(sw);
	          getJspContext().getOut().println(sw.toString());
	      }
	      
	      
	}
	
	
	
	
	
	
}
