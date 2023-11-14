package com.javatiaocao.myblog.constant;//Tracy He
//Oct 19th

public class Temperature{
   private char scale;
   private double tempValue = 10.0;
   private static int age = 10;
    Odometer Odometer;

//constructors
   public Temperature(double tv)//Set tempValue to tv and set scale to ‘C’
      {
      tempValue=tv;
      scale='C';
         System.out.println(age);

      
      }

   public Temperature(char s)//Set tempValue to 0 and scale to s
      {
      tempValue=0.0;
      scale=s;
      }  
   public Temperature(double tv, char s)//Set values to tv and s
      {
      tempValue=tv;
      scale=s;
         System.out.println(age);
      }
   public Temperature()//Set tempValue to 0, scale to ‘C’
      {
      tempValue=0.0;
      scale='C';
      }
      
   //getC
   public double getC(){
      if (scale==('C'))
         return tempValue;
      else
      {
      double degreeC;
         degreeC = (5 * (tempValue - 32)/9);  
         scale = 'C';
//         tempValue = degreeC;
      return degreeC;   
      }
   }

   public static void getD(){
      System.out.println("什么是静态");
   }
   //getF
   public   double getF(){
      if (scale==('F'))
         return tempValue;
      else
      {
      double degreeF;
         degreeF = 9.0*(tempValue/5.0)+32;
         scale = 'F';
         tempValue = degreeF;
      return degreeF;
      } 
   }
   
   //setter
   public void setTemp(double tv){
      tempValue=tv;
      }

   public void setTemp(double tv,int a){
      tempValue=tv;
   }
   public void setScale(char s){
      scale=s;
     }
  
   //equal method
   public boolean equals(Temperature other){

      if (this.scale == 'F'){
         other.getF(); //call the getF method to convert the other temperature to F
         }
      else{
         other.getC(); //call the getC method to convert the other temperature to 
         }
  
      return this.tempValue==other.tempValue;
      }   
   //toString
   public String toString(){
      return tempValue +" degrees "+scale;
      }

   @Override
   public boolean equals(Object obj) {

      return super.equals(obj);
   }
}