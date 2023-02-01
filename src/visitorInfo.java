
public class visitorInfo {
	public String fName,lName,phone;
	public int region,type;
	public ArrayStack<Integer>order;

	visitorInfo(String[] info){
		fName = info[0];
		lName = info[1];
		region = Integer.parseInt(info[2]);
		phone = info[4];
		type = Integer.parseInt(info[3]);
		order = new ArrayStack<>(info.length - 5);
		for(int i = 5; i< info.length; i++){
			order.push(Integer.parseInt(info[i]));
		}
	}

	public void printVisitorInfo(){
		System.out.println("Name: "+fName+" "+lName);
		System.out.println("Region: "+region);
		System.out.println("Phone: "+phone);
		System.out.println("Type: "+(type==1?"Vip":"Regular"));
		System.out.println("Order: "+order.toString());
	}

}
