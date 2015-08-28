/**
 *Written by e1301488,08/25/2015
 * 
 */
package cyclomaticcomplexity.handlers;


import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.IEditorSite;
import org.eclipse.jface.text.TextSelection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Image;


public class mainHandler extends AbstractHandler 
{
	/**
	 * The constructor
	 */
	public mainHandler() 
	{

	}	

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException 
	{
		/*
		 * Get the selected lines of code from the active editor
		 * we will store the text selection as a string,called "selectedText"
		 */
		
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
        ISelection iSelection = null;
        IEditorSite iEditorSite = window.getActivePage().getActiveEditor().getEditorSite();
        
        if (iEditorSite != null) 
        {
            ISelectionProvider iSelectionProvider = iEditorSite.getSelectionProvider();
            if (iSelectionProvider != null)
            {    	
                iSelection = iSelectionProvider.getSelection();
                if(!iSelection.isEmpty())
                {   
	                try
	                {
	                	String selectedText = ((TextSelection)iSelection).getText();
	                    
	                    /*
	                     * We set the default image of MessageDialog objects
	                     * we will create an instance of a custom dialog,to display
	                     * our complexity as a dialog box.
	                     * The constructor accepts the current window shell,and the 
	                     * calculated cyclomatic complexity of the selectedText,as an integer
	                     */
	                    MessageDialog.setDefaultImage(loadImage("icons/sample.gif",true));                 
	                    mydialog dial=new mydialog(window.getShell(),cycloComp(selectedText));
	                    dial.open();
	                	
	                }
	                catch(Exception e)
	                {
	                	MessageDialog.openError(window.getShell(), "Error",e.getMessage());
	                }
                }  
             }
          }
		
		return null;
	}

	/**
	 * the cycloComp will calculate the cyclomatic complexity of the selected
	 * text and will return a complexity rating,calculated after the formula:
	 * IFs+FORs+WHILEs+CASEs+1
	 */
	private int cycloComp(String toAnalyse)
	{
		/*
		 * bugfix:delete all the strings,which might contain
		 * words similar to control flow:char x[]="if(..)";
		 */
		toAnalyse=toAnalyse.replaceAll("\"(.*)\"", ""); 

		/*
		 * "complexityCounter" will be incremented each time a control flow
		 * statement is being met."lines" is a string array which stores each
		 * individual line of code.
		 * 
		 */
		int complexityCounter=1;
		String[] lines = toAnalyse.split("\n");
		boolean commentStarted=false;
		
		/*
		 * will iterate through each line of code,discard comments and record
		 * each occurence of control flow statements
		 */
		for(int i=0;i<lines.length;i++)
		{
			/*
			 * check if lines start with "//" comment and discard them
			 */
			
			String pattern = "^(\\s)*(//)(.*)";
			Pattern r = Pattern.compile(pattern);
			Matcher m = r.matcher(lines[i]);
			if( m.find() )
			{
				continue;
			}
			else
			{
				/*
				 * check if lines start with "/*" comment and record starting of
				 * commenting line,by setting commentStarted true.
				 * discard all the upcoming lines starting with "*" until
				 * the end of the comment section is reached 
				 */
				 pattern = "^(\\s)*(/\\*)(.*)";
				 r = Pattern.compile(pattern);
				 m = r.matcher(lines[i]);
				 if( m.find() )
				 {
					 commentStarted=true;
				 	 continue;
				 }
				 else
				 {
					 /*
					  * if the comments ended (with a "/*") set the variable
					  * commentStarted to false,in order to stop considering all the
					  * further lines as comments
					  */
					 pattern = "^(\\s)*(\\*/)(.*)";
					 r = Pattern.compile(pattern);
					 m = r.matcher(lines[i]);
					 if( m.find() )
					 {
						 commentStarted=false;
					 	 continue;
					 }
					 else if(commentStarted==true)
					 {
						 continue;
					 }
					 else
					 { 
						 pattern = "((.*)(if)(\\s)*(\\().*)|"+
			      					"((.*)(while)(\\s)*(\\().*)|"+
			      					"((.*)(for)(\\s)*(\\().*)|"+
			      					"((.*)(case)(.*)(\\:).*)";
						 r = Pattern.compile(pattern);
						 m = r.matcher(lines[i]);
						 if( m.find() )
						 {
							 complexityCounter++;
						 }
					 }
				 }	
			}		 
		}
		
		return  complexityCounter;
	}

	public static Image loadImage(String path, boolean inJar)
	{
	    Image newImage = null;
	    
	    try
	    {
	        if(inJar)
	        {
	            newImage = new Image(null, mainHandler.class.getClassLoader().getResourceAsStream(path));
	        }
	        else
	        {
	            newImage = new Image(null, path);
	        }
	    }
	    catch(SWTException ex)
	    {
	        System.out.println("Couldn't find " + path);
	    }

	    return newImage;
	}
}

