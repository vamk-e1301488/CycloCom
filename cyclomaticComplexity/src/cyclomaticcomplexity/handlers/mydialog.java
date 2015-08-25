package cyclomaticcomplexity.handlers;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;


public class mydialog extends Dialog {

	  private int complexity=0;
	  public mydialog(Shell parentShell,int comp)
	  {
	    super(parentShell);
	    complexity=comp;
	  }
	  
	  @Override
	  protected Control createDialogArea(Composite parent) 
	  {
	    Composite container = (Composite) super.createDialogArea(parent);
        RowLayout layout = new RowLayout();
        layout.wrap = false;
        container.setLayout(layout);
	    Label lab = new Label(container, SWT.PUSH);
	    /*
	     * if the complexity is low,set green image
	     */
	    if(complexity<20)
	    {
	    	lab.setImage(loadImage("icons/green.png",true));
	    }
	    else if(complexity>=21&&complexity<50)
	    {
	    	lab.setImage(loadImage("icons/yellow.png",true));
	    }
	    else if(complexity>50)
	    {
	    	lab.setImage(loadImage("icons/red.png",true));
	    }

	    Label lab2 = new Label(container, SWT.PUSH);
	    lab2.setText("Your code's complexity is: "+String.valueOf(complexity));

	    
	    return container;
	  }
	  @Override
	  protected void configureShell(Shell newShell) {
	    super.configureShell(newShell);
	    newShell.setText("Cyclomatic complexity");
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
