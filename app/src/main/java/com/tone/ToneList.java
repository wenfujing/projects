package com.tone;

public class ToneList {
	private String title;
	private String time;
	private String finish;
	private String typeid;
	private String toneid;
	
	public void setTitle(String title){
		this.title = title;
	}
	public void setTypeid(String typeid){
		this.typeid = typeid;
	}
	public void setToneid(String toneid){
		this.toneid = toneid;
	}
	public void setTime(String time){
		this.time = "����ʱ�䣺 "+time;
	}
	public void setFinish(int finish){
		//�ж��Ƿ���ɣ�1Ϊ����ɣ�0Ϊδ���
		if(finish==0){
			this.finish="δ���";
		}else{
			this.finish="�����";
		}
	
	}
	public String getTitle(){
		return title;
	}
	public String getTime(){
		return time;
	}
	public String getFinish(){
		return finish;
	}
	public String getToneid(){
		return toneid;
	}
	public String getTypeid(){
		return typeid;
	}
	

	

}
