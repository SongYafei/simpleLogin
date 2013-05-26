package com.procit.simplelogin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import android.content.Context;
import android.os.Environment;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

public class XmlCreate {
	
	private DocumentBuilderFactory docFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private Element rootElement, userElement, idElem, nameElem, locsElem, emailElem, phoneElem;
	private Environment envn;
	private String[] xmlName = new String[4];
	private static HashMap<Integer,String> hMap = new HashMap<Integer, String>();
	private int whose;  //id of the user
	private Context context;
	
	public XmlCreate(Context context, int userId)
	{
		this.context = context;
		this.whose = userId;
		createNewDoc();
		
	}

/******* To create a new xml Document(NOT FILE). It has to be created everytime *****/
	public void createNewDoc() {
		// TODO Auto-generated method stub
		try {
			
			this.docFactory = DocumentBuilderFactory.newInstance();
			this.docBuilder = this.docFactory.newDocumentBuilder();
			this.doc = this.docBuilder.newDocument();
			//For root Element
			this.rootElement = this.doc.createElement("UserDetails");
			this.doc.appendChild(this.rootElement);
			//For User Element
			this.userElement = this.doc.createElement("User");
			this.rootElement.appendChild(this.userElement);
			//For 3rd degree id elements
			this.idElem = this.doc.createElement("id");
			this.idElem.appendChild(this.doc.createTextNode("1"));
			this.userElement.appendChild(this.idElem);
			//For 3rd degree name elements
			this.nameElem = this.doc.createElement("name");
			this.nameElem.appendChild(this.doc.createTextNode("Ram"));
			this.userElement.appendChild(this.nameElem);
			//For 3rd degree Location elements
			this.locsElem = this.doc.createElement("Location");
			this.locsElem.appendChild(this.doc.createTextNode("basantapur"));
			this.userElement.appendChild(this.locsElem);
			//For 3rd degree email elements
			this.emailElem = this.doc.createElement("Email");
			this.emailElem.appendChild(this.doc.createTextNode("ram@aloo.com"));
			this.userElement.appendChild(this.emailElem);
			//For 3rd degree Phone elements
			this.phoneElem = this.doc.createElement("Phone");
			this.phoneElem.appendChild(this.doc.createTextNode("8998877"));
			this.userElement.appendChild(this.phoneElem);
			
			 String str=getStringFromDocument(doc);
			 Log.v("str: ",""+str);
			 createNewFile(str);
		//To read xml from the SDCard 
			// readFromSDCard();
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//************************Read from SDCard****************************/
		public void readFromSDCard() 
		{
		  try{	
			File myFile = new File(Environment.getExternalStorageDirectory(),xmlName[this.whose]);
      
			this.docFactory = DocumentBuilderFactory.newInstance();
			this.docBuilder = this.docFactory.newDocumentBuilder();
			this.doc = this.docBuilder.parse(myFile);
			this.doc.getDocumentElement().normalize();
			
			Element user = (Element)this.doc.getElementsByTagName("name").item(0);
			Element location = (Element) this.doc.getElementsByTagName("Location").item(0);
			Element id = (Element) this.doc.getElementsByTagName("id").item(0);
			Element email = (Element) this.doc.getElementsByTagName("Email").item(0);
			Element phone = (Element) this.doc.getElementsByTagName("Phone").item(0);
			

			Log.v("Id: "+id.getTextContent(), "id done");
			Log.v("Name: "+user.getTextContent(), "name dine");
			Log.v("Location: "+location.getTextContent(), "locatin done");
			Log.v("Email: "+email.getTextContent(), "Email done");
			Log.v("Phone: "+phone.getTextContent(), "Phone dine");
			
            Toast.makeText(this.context,"Done reading SD 'abc.txt'\n",Toast.LENGTH_SHORT).show();
            
		  }
		  catch(Exception e) {
  
			  Toast.makeText(this.context, e.getMessage(),Toast.LENGTH_SHORT).show();

		  	}

		  Log.v("File saved!","done");
	   }

		
//**********To create a new XML file to write the details of the users********************
	    private void createNewFile(String xmlStr) {
		// TODO Auto-generated method stub
		String state = this.envn.getExternalStorageState();
		if(state.equals(Environment.MEDIA_MOUNTED))
		{
			//Do for Sdcard present
			Log.v("r/o","excellent");
			// TO decide creating or editing, do here
			if(!hMap.containsKey(this.whose))
				{	Log.v("Key was never present","phoof");
					this.xmlName[this.whose] =  namingConvention(this.whose);
					hMap.put(this.whose, this.xmlName[this.whose]);
					Log.v("HashMap: ", ""+hMap);
				}
			else
				{	
					this.xmlName[this.whose] = hMap.get(this.whose);
					Log.v("Previous Name reused",""+this.xmlName[this.whose]);
				}
			
			File file = new File(Environment.getExternalStorageDirectory(),xmlName[this.whose]);
			try {
				file.createNewFile();
				FileOutputStream fOut = new FileOutputStream(file);

                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);

                myOutWriter.append(xmlStr);

                myOutWriter.close();

                fOut.close();

                
			Toast.makeText(this.context,"Done writing SD 'mysdfile.txt'",Toast.LENGTH_SHORT).show();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(state.equals(Environment.MEDIA_MOUNTED_READ_ONLY))
		{
			//Do for read/Only access
			Log.v("rread only","good");
		}
		else 
		{
			Log.v("Dont know what","poor ");
		}
	}

 //***********method to convert Document to String*****************
		public String getStringFromDocument(Document doc)

	    {

	        try

	        {

	           DOMSource domSource = new DOMSource(this.doc);

	           StringWriter writer = new StringWriter();

	           StreamResult result = new StreamResult(writer);

	           TransformerFactory tf = TransformerFactory.newInstance();

	           Transformer transformer = tf.newTransformer();

	           transformer.transform(domSource, result);

	           return writer.toString();

	        }

	        catch(TransformerException ex)

	        {

	           ex.printStackTrace();

	           return null;

	        }

	    }
		
/*****Get current timestamp and date****/
		public String namingConvention(int id)
		{
			StringBuilder strBuild = new StringBuilder();
			Time time = new Time();
			String timeFormat;
			Random rand = new Random();
			int randPosInt = rand.nextInt()%1000;
			if(randPosInt < 0)
				randPosInt -= 2*randPosInt;
			DateFormat dFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
			Date date = new Date();
			timeFormat = dFormat.format(date);
			strBuild.append(timeFormat);
			strBuild.append("_"+randPosInt);
			strBuild.append("_"+id);
			strBuild.append(".xml");
			timeFormat = strBuild.toString();
			Log.v("New Date Format: "+timeFormat," Created");
			return timeFormat;
			
		}

}
   