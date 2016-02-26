package template;

public class CommonBlockTemplate {
	
	public String schoolList(String[] s){
		StringBuffer sb= new StringBuffer();
		Integer cnumber = (Integer)s.length;
		for(Integer i = 0 ; i < cnumber ; i++){
			sb.append("<li onclick='schoolBox.comfirmSelect({name:\""+s[i].toString()+"\"})' style = 'float:left;width:195px;margin-top:5px;text-align:left;list-style:none;font-size:12px;cursor:pointer;'><span class='sb_school_list' style='float:left;color:#e68303;'>"+s[i].toString()+"</span></li>");
		}
		return sb.toString();
	}
}
