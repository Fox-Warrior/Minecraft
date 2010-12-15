package com.foxwarrior.util;

public class MathFunctions {
    
	public static int Clamp(int MinValue, int MaxValue, int Value){
		int Result = 0;
		if(Value < MinValue ){
			Result = MinValue;
		} else if(Value > MaxValue){
			Result = MaxValue;
		} else {
			Result = Value;
		}
		return Result;	
	}

}
