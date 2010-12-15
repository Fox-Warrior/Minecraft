package com.foxwarrior.util;
import java.io.*;
import java.lang.reflect.Array;
import java.util.zip.*;
import java.util.ArrayList;

public class ArrayManager {
      
	  public static void saveArrayList_String(String filename, ArrayList<String> Output_Stream) {
		     try {
		        FileOutputStream FileToSave = new FileOutputStream(filename);
		        GZIPOutputStream FileCompressed = new GZIPOutputStream(FileToSave);
		        ObjectOutputStream out = new ObjectOutputStream(FileCompressed);
		        out.writeObject(Output_Stream);
		        out.flush();
		        out.close();
		     }
		     catch (IOException e) {
		         System.out.println(e); 
		     }
		  }
	  
	  public static void saveArrayList_Integer(String filename, ArrayList<Integer> Output_Stream) {
		     try {
		        FileOutputStream FileToSave = new FileOutputStream(filename);
		        GZIPOutputStream FileCompressed = new GZIPOutputStream(FileToSave);
		        ObjectOutputStream out = new ObjectOutputStream(FileCompressed);
		        out.writeObject(Output_Stream);
		        out.flush();
		        out.close();
		     }
		     catch (IOException e) {
		         System.out.println(e); 
		     }
		  }
	  
	  public static void saveArrayList_Boolean(String filename, ArrayList<Boolean> Output_Stream) {
		     try {
		        FileOutputStream FileToSave = new FileOutputStream(filename);
		        GZIPOutputStream FileCompressed = new GZIPOutputStream(FileToSave);
		        ObjectOutputStream out = new ObjectOutputStream(FileCompressed);
		        out.writeObject(Output_Stream);
		        out.flush();
		        out.close();
		     }
		     catch (IOException e) {
		         System.out.println(e); 
		     }
		  }
	  
	  public static void saveArrayList_Double(String filename, ArrayList<Double> Output_Stream) {
		     try {
		        FileOutputStream FileToSave = new FileOutputStream(filename);
		        GZIPOutputStream FileCompressed = new GZIPOutputStream(FileToSave);
		        ObjectOutputStream out = new ObjectOutputStream(FileCompressed);
		        out.writeObject(Output_Stream);
		        out.flush();
		        out.close();
		     }
		     catch (IOException e) {
		         System.out.println(e); 
		     }
		  }
	  
	  public static void saveArrayList_Byte(String filename, ArrayList<Byte> Output_Stream) {
		     try {
		        FileOutputStream FileToSave = new FileOutputStream(filename);
		        GZIPOutputStream FileCompressed = new GZIPOutputStream(FileToSave);
		        ObjectOutputStream out = new ObjectOutputStream(FileCompressed);
		        out.writeObject(Output_Stream);
		        out.flush();
		        out.close();
		     }
		     catch (IOException e) {
		         System.out.println(e); 
		     }
		  }
	  
	  public static void saveArrayList_Bytes(String filename, ArrayList<Byte[]> Output_Stream) {
		     try {
		        FileOutputStream FileToSave = new FileOutputStream(filename);
		        GZIPOutputStream FileCompressed = new GZIPOutputStream(FileToSave);
		        ObjectOutputStream out = new ObjectOutputStream(FileCompressed);
		        out.writeObject(Output_Stream);
		        out.flush();
		        out.close();
		     }
		     catch (IOException e) {
		         System.out.println(e); 
		     }
		  }
	  
	  public static void saveObject(String filename, Object Output_Stream) {
		     try {
		        FileOutputStream FileToSave = new FileOutputStream(filename);
		        GZIPOutputStream FileCompressed = new GZIPOutputStream(FileToSave);
		        ObjectOutputStream out = new ObjectOutputStream(FileCompressed);
		        out.writeObject(Output_Stream);
		        out.flush();
		        out.close();
		     }
		     catch (IOException e) {
		         System.out.println(e); 
		     }
		  }
	  
	  public static void saveArray_String(String filename, Array Output_Stream) {
		     try {
		        FileOutputStream FileToSave = new FileOutputStream(filename);
		        GZIPOutputStream FileCompressed = new GZIPOutputStream(FileToSave);
		        ObjectOutputStream out = new ObjectOutputStream(FileCompressed);
		        out.writeObject(Output_Stream);
		        out.flush();
		        out.close();
		     }
		     catch (IOException e) {
		         System.out.println(e); 
		     }
		  }

	  public static ArrayList<Integer> loadArrayList_Integer(String filename) {
		      try {
		        FileInputStream FileToSave = new FileInputStream(filename);
		        GZIPInputStream FileDeCompressed = new GZIPInputStream(FileToSave);
		        ObjectInputStream in = new ObjectInputStream(FileDeCompressed);
		        @SuppressWarnings("unchecked")
				ArrayList<Integer> Output_Stream = (ArrayList<Integer>)in.readObject();
		        in.close();
		        return Output_Stream;
		      }
		      catch (Exception e) {
		          System.out.println(e);
		      }
		      return null;
		  }
		  
	  public static ArrayList<String> loadArrayList_String(String filename) {
		      try {
		        FileInputStream FileToSave = new FileInputStream(filename);
		        GZIPInputStream FileDeCompressed = new GZIPInputStream(FileToSave);
		        ObjectInputStream in = new ObjectInputStream(FileDeCompressed);
		        @SuppressWarnings("unchecked")
				ArrayList<String> Output_Stream = (ArrayList<String>)in.readObject();
		        in.close();
		        return Output_Stream;
		      }
		      catch (Exception e) {
		          System.out.println(e);
		      }
		      return null;
		  }
		  
	  public static ArrayList<Boolean> loadArrayList_Boolean(String filename) {
		      try {
		        FileInputStream FileToSave = new FileInputStream(filename);
		        GZIPInputStream FileDeCompressed = new GZIPInputStream(FileToSave);
		        ObjectInputStream in = new ObjectInputStream(FileDeCompressed);
		        @SuppressWarnings("unchecked")
				ArrayList<Boolean> Output_Stream = (ArrayList<Boolean>)in.readObject();
		        in.close();
		        return Output_Stream;
		      }
		      catch (Exception e) {
		          System.out.println(e);
		      }
		      return null;
		  }
	  
	  public static ArrayList<Double> loadArrayList_Double(String filename) {
	      try {
	        FileInputStream FileToSave = new FileInputStream(filename);
	        GZIPInputStream FileDeCompressed = new GZIPInputStream(FileToSave);
	        ObjectInputStream in = new ObjectInputStream(FileDeCompressed);
	        @SuppressWarnings("unchecked")
			ArrayList<Double> Output_Stream = (ArrayList<Double>)in.readObject();
	        in.close();
	        return Output_Stream;
	      }
	      catch (Exception e) {
	          System.out.println(e);
	      }
	      return null;
	  }
	  
	  public static ArrayList<Byte> loadArrayList_Byte(String filename) {
	      try {
	        FileInputStream FileToSave = new FileInputStream(filename);
	        GZIPInputStream FileDeCompressed = new GZIPInputStream(FileToSave);
	        ObjectInputStream in = new ObjectInputStream(FileDeCompressed);
	        @SuppressWarnings("unchecked")
			ArrayList<Byte> Output_Stream = (ArrayList<Byte>)in.readObject();
	        in.close();
	        return Output_Stream;
	      }
	      catch (Exception e) {
	          System.out.println(e);
	      }
	      return null;
	  }
	  
	  public static ArrayList<Byte[]> loadArrayList_Bytes(String filename) {
	      try {
	        FileInputStream FileToSave = new FileInputStream(filename);
	        GZIPInputStream FileDeCompressed = new GZIPInputStream(FileToSave);
	        ObjectInputStream in = new ObjectInputStream(FileDeCompressed);
	        @SuppressWarnings("unchecked")
			ArrayList<Byte[]> Output_Stream = (ArrayList<Byte[]>)in.readObject();
	        in.close();
	        return Output_Stream;
	      }
	      catch (Exception e) {
	          System.out.println(e);
	      }
	      return null;
	  }
	  
	  public static Object loadObject(String filename) {
	      try {
	        FileInputStream FileToSave = new FileInputStream(filename);
	        GZIPInputStream FileDeCompressed = new GZIPInputStream(FileToSave);
	        ObjectInputStream in = new ObjectInputStream(FileDeCompressed);
			Object Output_Stream = (Object)in.readObject();
	        in.close();
	        return Output_Stream;
	      }
	      catch (Exception e) {
	          System.out.println(e);
	      }
	      return null;
	  }
	    
	  public static Array loadArray(String filename) {
	      try {
	        FileInputStream FileToSave = new FileInputStream(filename);
	        GZIPInputStream FileDeCompressed = new GZIPInputStream(FileToSave);
	        ObjectInputStream in = new ObjectInputStream(FileDeCompressed);
			Array Output_Stream = (Array)in.readObject();
	        in.close();
	        return Output_Stream;
	      }
	      catch (Exception e) {
	          System.out.println(e);
	      }
	      return null;
	  }

}
