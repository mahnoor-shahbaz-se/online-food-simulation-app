import java.util.*;

public class OnlineFoodSimulationApp {

    // MENU DATA: code, name, price
    static String[][] burgerMenu = {
            {"101","Zinger Burger","550"},
            {"102","Beef Burger","650"},
            {"103","Fries","250"}
    };

    static String[][] pizzaMenu = {
            {"201","Chicken Pizza","1200"},
            {"202","Fajita Pizza","1400"},
            {"203","Garlic Bread","300"}
    };

    static String[][] desiMenu = {
            {"301","Chicken Karahi","1100"},
            {"302","Biryani","350"},
            {"303","Raita","80"}
    };

    // choose menu
    static String[][] getMenu(int r) {
        if (r == 1) return burgerMenu;
        if (r == 2) return pizzaMenu;
        return desiMenu;
    }

    static void displayRestaurants() {
        System.out.println("1. Burger House");
        System.out.println("2. Pizza Palace");
        System.out.println("3. Desi Delight");
    }

    static void displayMenu(String[][] menu) {
        System.out.println("\nCode\tItem\t\tPrice");
        for(int i=0;i<menu.length;i++)
            System.out.println(menu[i][0]+"\t"+menu[i][1]+"\t"+menu[i][2]);
    }

    static boolean validCode(String[][] menu, String code) {
        for(int i=0;i<menu.length;i++)
            if(menu[i][0].equals(code))
                return true;
        return false;
    }

    static int getPrice(String[][] menu, String code) {
        for(int i=0;i<menu.length;i++)
            if(menu[i][0].equals(code))
                return Integer.parseInt(menu[i][2]);
        return 0;
    }

    static String getName(String[][] menu, String code) {
        for(int i=0;i<menu.length;i++)
            if(menu[i][0].equals(code))
                return menu[i][1];
        return "";
    }

    static int subtotal(String[][] menu, String[] codes, int[] qty) {
        int sum=0;
        for(int i=0;i<codes.length;i++)
            sum += getPrice(menu,codes[i]) * qty[i];
        return sum;
    }

    static int discount(int sub) {
        if(sub>3000) return (int)(sub*0.10);
        return 0;
    }

    static void receipt(String[][] menu,String[] codes,int[] qty,int sub,int disc) {
        System.out.println("\n------ RECEIPT ------");
        for(int i=0;i<codes.length;i++) {
            String name=getName(menu,codes[i]);
            int price=getPrice(menu,codes[i]);
            System.out.println(name+" x"+qty[i]+" = "+price*qty[i]);
        }
        System.out.println("---------------------");
        System.out.println("Subtotal: "+sub);
        System.out.println("Discount: "+disc);
        System.out.println("Final Bill: "+(sub-disc));
    }

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);

        displayRestaurants();
        System.out.print("Select restaurant: ");
        int r=sc.nextInt();

        if(r<1 || r>3) {
            System.out.println("Invalid restaurant!");
            return;
        }

        String[][] menu=getMenu(r);
        displayMenu(menu);

        System.out.print("\nHow many items: ");
        int n=sc.nextInt();

        String[] codes=new String[n];
        int[] qty=new int[n];

        for(int i=0;i<n;i++) {

            System.out.print("Enter item code: ");
            String code=sc.next();

            while(!validCode(menu,code)) {
                System.out.print("Invalid code, re-enter: ");
                code=sc.next();
            }

            System.out.print("Enter quantity: ");
            int q=sc.nextInt();

            while(q<=0) {
                System.out.print("Enter positive quantity: ");
                q=sc.nextInt();
            }

            codes[i]=code;
            qty[i]=q;
        }

        int sub=subtotal(menu,codes,qty);
        int disc=discount(sub);
        receipt(menu,codes,qty,sub,disc);

        sc.close();
    }
}