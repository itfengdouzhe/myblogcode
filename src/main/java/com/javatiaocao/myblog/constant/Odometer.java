package com.javatiaocao.myblog.constant;//Tracy He
//Oct 23rd

public class Odometer{
   private double milesDriven;
   private double mpg;
  
  //constructor 
   public Odometer(){
      milesDriven=0.0;
      mpg=0.0;
      }
   public Odometer(double mD, double m){
      milesDriven=mD;
      mpg=m;
      }
  
  //getter
   public double getMilesDriven(){
      return milesDriven;
      }
   public double getMPG(){
      return mpg;
      }
      
   //setter
   public void setMilesDriven(double o){
      milesDriven=o;
      }
   public void setMPG(double m){
      mpg=m;
      }
      
   //reset milesdriven
   public void resetMilesDriven(){
      milesDriven=0;
      }
      
   //add milesdriven
   public void addMilesDriven(double d){
      milesDriven+=d;
      }
   
   //gallon used
   public double gallonsUsed(){
      return (milesDriven/mpg);
      }
   
   //toString
   public String toString(){
      return milesDriven+" miles had driven and the mpg is "+mpg;
      }
      
   //equal
   public boolean equals(Odometer other){
      return (this.milesDriven==other.milesDriven && this.mpg==other.mpg);
      }
      
  //Compared to 
  public int compareTo(Odometer other){
     if(this.milesDriven>other.milesDriven)
        return 1;
     else if(this.milesDriven<other.milesDriven)
        return -1;
     else
        return 0;
     }

    public int compareTo(Odometer other,int a){
        if(this.milesDriven>other.milesDriven)
            return 1;
        else if(this.milesDriven<other.milesDriven)
            return -1;
        else
            return 0;
    }
 }
     
      
      