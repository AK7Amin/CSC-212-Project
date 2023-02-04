import java.io.*;
import java.util.Objects;

public class ThemeParkADT {
    private rgnInfoArray regionArray;
    private rgnInfoArray rgnSortedArray;
    private int totalRegions;
    private LinkedList<visitorInfo> visitors;
    private LinkedList<visitorInfo> vips;

    public void readFileAndAnalyse(String f) {
        try {
            //r = number of regions
            totalRegions = 0;

            //k = max region
            int k = readFile(f);


            regionArray = new rgnInfoArray(k + 1);
            totalRegions = insertRegions(visitors, regionArray.data);

            rgnInfoArray arr = new rgnInfoArray(k + 1);
            totalRegions = insertRegions(visitors, arr.data);
            sortRegions(arr.data, k + 1);


            rgnSortedArray = new rgnInfoArray(totalRegions);

            for (int i = 0; i < totalRegions; i++) {

                rgnSortedArray.data[i] = arr.data[i];
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private int readFile(String f) throws IOException, ClassNotFoundException, Exception {
        int maxRegion = 0;
        vips = new LinkedList();
        visitors = new LinkedList();

        //open file F
        File inF = new File(f);
        FileReader FR = new FileReader(inF);
        BufferedReader BFR = new BufferedReader(FR);

        String currentLine = "";
        boolean whiteSpaceFound = false;

        //loop over lines in file F,
        // convert text to visitor info,
        // then add visitors to their corresponding lists
        try {
            while (true) {
                currentLine = BFR.readLine();

                String[] info = currentLine.split(",");

                whiteSpaceFound = checkForWhitespace(info);
                if (whiteSpaceFound) break;

                visitorInfo visitor = new visitorInfo(info);

                visitors.insert(visitor);
                if (visitor.type == 1) vips.insert(visitor);
                if (visitor.region > maxRegion) maxRegion = visitor.region;
            }

        } catch (Exception e) {
            //System.out.println(e);
        }
        BFR.close();
        FR.close();

        if (whiteSpaceFound) {
            throw new Exception("\n!!!!!Whitespace is not allowed in input!!!!!\n");
        }

        return maxRegion;
    }

    private boolean checkForWhitespace(String[] info) {
        boolean whiteSpaceFound = false;
        for (int i = 0; i < info.length; i++) {
            char[] chars = info[i].toCharArray();
            for (int j = 0; j < chars.length; j++) {
                if (Character.isWhitespace(chars[j])) {

                    whiteSpaceFound = true;
                }
            }
        }
        return whiteSpaceFound;
    }

    private void sortRegions(rgnInfo[] arr, int n) {
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j].getTotal_visitors() < arr[j + 1].getTotal_visitors()) {
                    rgnInfo temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    private int insertRegions(LinkedList<visitorInfo> visitors, rgnInfo[] array) {
        int totalRegions = 0;
        visitors.findfirst();
        visitorInfo temp = visitors.retrieve();

        while (temp != null) {

            rgnInfo currentRegion = array[temp.region];

            if (currentRegion.total_visitors == 0) {
                totalRegions++;
                array[temp.region].region = temp.region;
            }
            int x = temp.type == 1 ? 0 : 1;
            currentRegion.vtype[x].visitList.insert(temp);

            currentRegion.vtype[x].num_visitors++;
            currentRegion.total_visitors++;

            visitors.findnext();
            temp = visitors.retrieve();
        }
        return totalRegions;
    }


    //Operation 1
    public void searchVisitor(String lName) {
        int n = 1;

        visitors.findfirst();
        visitorInfo visitor = visitors.retrieve();

        while (visitor != null) {

            if (visitor.lName.equalsIgnoreCase(lName)) {
                printVisitorData(n++, visitor);
            }

            visitors.findnext();
            visitor = visitors.retrieve();
        }

        if (n == 1) {
            System.out.println("No visitors with the last name '" + lName + "' found");
        }
    }

    private void printVisitorData(int n, visitorInfo v) {
        System.out.println("Visitor " + n + ":");
        System.out.println("Name: " + v.fName + " " + v.lName);
        System.out.println("Region: " + v.region);
        System.out.println("VIP Pass holder: " + (v.type == 1 ? "Yes" : "No"));
        System.out.println("Phone number: " + v.phone);

        System.out.print("Order of visiting the kingdoms: ");
        Integer x = v.order.pop();
        v.order.push(x);
        printStack(v.order, x);
        System.out.println(" ");

    }

    private void printStack(ArrayStack<Integer> st, Integer n) {
        if (st.empty()) return;

        Integer top = st.pop();
        printStack(st, n);

        if (top == n) {
            System.out.println(top);
        } else {
            System.out.print(top + ",");
        }

        st.push(top);
    }

    //Operation 2
    public void rgnCount() {
        System.out.println("The total number of regions are " + totalRegions);
    }

    //Operation 3
    public void popularRgn() {
        for (int i = 0; i < rgnSortedArray.data.length; i++) {
            System.out.println("Region " + rgnSortedArray.data[i].region + ": " + rgnSortedArray.data[i].total_visitors);
        }
    }

    //Operation 4
    public void vipRgn(int r) {
        if (r > totalRegions) {
            System.out.println("The total number of VIP pass holders coming from Region " + r + " is " + 0);
            return;
        }
        for (int i = 0; i < regionArray.data.length; i++) {
            if (regionArray.data[i].region == r)
                System.out.println("The total number of VIP pass holders coming from Region " + r + " is " + regionArray.data[i].vtype[0].num_visitors);
        }
    }

    //Operation 5
    public void vipLocation() {
        vips.findfirst();
        visitorInfo visitor = vips.retrieve();
        while (visitor != null) {

            Integer kingdom = visitor.order.pop();
            System.out.println(visitor.fName + " " + visitor.lName + " in Kingdom " + kingdom);
            visitor.order.push(kingdom);

            vips.findnext();
            visitor = vips.retrieve();
        }
    }

    //Operation 6
    public boolean checkVipLoc(String n1, String n2) {
        boolean flag;
        visitorInfo v1 = null;
        visitorInfo v2 = null;

        vips.findfirst();
        visitorInfo v = vips.retrieve();

        while ((v != null) && (v1 == null || v2 == null)) {

            if (v.phone.equals(n1)) v1 = v;
            else if (v.phone.equals(n2)) v2 = v;

            vips.findnext();
            v = vips.retrieve();
        }
        if (v1 == null || v2 == null) {
            if (v1 == null) {
                System.out.println("There is no visitor with the number: " + n1 + " in the vip list");
            }
            if (v2 == null) {
                System.out.println("There is no visitor with the number: " + n2 + " in the vip list");
            }
            return false;
        }
        else if (v1.region != v2.region) {
            System.out.println("Not in the same region.");
            return false;
        }
        else {
            Integer q = v1.order.pop();
            Integer w = v2.order.pop();

            flag = Objects.equals(q, w);

            v1.order.push(q);
            v2.order.push(w);
        }

        return flag;
    }

    //Operation 7
    public boolean checkRegLoc(int r, String n1, String n2) {
        visitorInfo v1 = null, v2 = null;
        boolean flag = false;
        visitors.findfirst();
        while (!visitors.last() && (v1 == null || v2 == null)) {
            visitorInfo v = visitors.retrieve();
            if (v.phone.equals(n1))
                v1 = v;
            else if (v.phone.equals(n2))
                v2 = v;
            visitors.findnext();

        }
        if (v1 == null || v2 == null) {
            if (v1 == null) {
                System.out.println("There is no visitor with number: " + n1 + ".");
            }
            if (v2 == null) {
                System.out.println("There is no visitor with number: " + n2 + ".");
            }
            return flag = false;
        }
        if (r != v1.region || r != v2.region) {
            if (r != v1.region) {
                System.out.println("The visitor with number: " + n1 + " is not from region " + r + ".");
            }
            if (r != v2.region) {
                System.out.println("The visitor with number: " + n2 + " is not from region " + r + ".");
            }
            return flag = false;
        }
        flag = sameVisitOrder(v1, v2, flag);
        return flag;

    }

    private boolean sameVisitOrder(visitorInfo v1, visitorInfo v2, boolean flag) {
        int top1 = 0;
        int top2 = 0;
        if (v1.order.empty())
            return flag = true;
        else {
            top1 = v1.order.pop();
            top2 = v2.order.pop();
        }

        if (top1 != top2) {
            System.out.println("Orders are not the same.");
            flag = false;
        } else if (flag != true)
            flag = sameVisitOrder(v1, v2, flag);
        v1.order.push(top1);
        v2.order.push(top2);
        return flag;
    }


}
