package tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class UTILS {

	public UTILS() {
		// TODO Auto-generated constructor stub
	}
	
	public static String readLastLine(File file, String charset) throws IOException 
	{
	  if (!file.exists() || file.isDirectory() || !file.canRead()) 
	  {
	    return null;
	  }
	  RandomAccessFile raf = null;
	  try 
	  {
	    raf = new RandomAccessFile(file, "r");
	    long len = raf.length();
	    if (len == 0L) 
	    {
	      return "";
	    }
	    else 
	    {
	      long pos = len - 1;
	      while (pos > 0) 
	      {
	        pos--;
	        raf.seek(pos);
	        if (raf.readByte() == '\n') 
	        {
	          break;
	        }
	      }
	      if (pos == 0) 
	      {
	        raf.seek(0);
	      }
	      byte[] bytes = new byte[(int) (len - pos)];
	      raf.read(bytes);
	      if (charset == null) 
	      {
	        return new String(bytes);
	      } 
	      else 
	      {
	        return new String(bytes, charset);
	      }
	    }
	  } 
	  catch (FileNotFoundException e) 
	  {
		  e.printStackTrace();
	  } 
	  finally 
	  {
	    if (raf != null)
	    {
	      try 
	      {
	        raf.close();
	      } 
	      catch (Exception e2)
	      {
	    	  e2.printStackTrace();
	      }
	    }
	  }
	  return null;
	}

}
