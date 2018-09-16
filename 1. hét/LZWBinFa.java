
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LZWBinFa extends HttpServlet{
  String[] szoveg=new String[50];
  public StringBuffer buff= new StringBuffer();
  int sor=0;

  public LZWBinFa() {

    fa = gyoker;

  }
  
  public void egyBitFeldolg(char b) {
    if (b == '0') {
      
      if (fa.nullasGyermek() == null)
      {
      
        Csomopont uj = new Csomopont('0');
        
        fa.ujNullasGyermek(uj);
        
        fa = gyoker;
      } else 
      {
        
        fa = fa.nullasGyermek();
      }
    }  else {
      if (fa.egyesGyermek() == null) {
        Csomopont uj = new Csomopont('1');
        fa.ujEgyesGyermek(uj);
        fa = gyoker;
      } else {
        fa = fa.egyesGyermek();
      }
    }
  }
  
  public void lement() {
    melyseg = 0;
    
    valami(gyoker);
    
buff.append("111111111111");
  }

 
  class Csomopont {

    
    public Csomopont(char betu) {
      this.betu = betu;
      balNulla = null;
      jobbEgy = null;
    }

    ;
    
    
    
    public Csomopont nullasGyermek() {
      return balNulla;
    }
    

    public Csomopont egyesGyermek() {
      return jobbEgy;
    }
    

    public void ujNullasGyermek(Csomopont gy) {
      balNulla = gy;
    }
    

    public void ujEgyesGyermek(Csomopont gy) {
      jobbEgy = gy;
    }
    

    public char getBetu() {
      return betu;
    }
    
    private char betu;
    
    private Csomopont balNulla = null;
    private Csomopont jobbEgy = null;
    
  };

  
  private Csomopont fa = null;
  private int melyseg, atlagosszeg, atlagdb;
  private double szorasosszeg;
  
   
  public void valami(Csomopont elem) {
	
   
	szoveg[30]="3333333333333333333";
    if (elem != null) {
      ++melyseg;
      valami(elem.egyesGyermek());
     
      for (int i = 0; i < melyseg; ++i) {
        
	szoveg[sor]+="---";
      }
	
      
      szoveg[sor]+=elem.getBetu();
      szoveg[sor]+="(";
      szoveg[sor]+=Integer.toString(melyseg - 1);
      szoveg[sor]+=")";

      sor+=1;
      --melyseg;
	valami(elem.nullasGyermek());
    }
	
  }
  
  protected Csomopont gyoker = new Csomopont('/');
  int maxMelyseg;
  double atlag, szoras;

  
  public int getMelyseg() {
    melyseg = maxMelyseg = 0;
    rmelyseg(gyoker);
    return maxMelyseg - 1;
  }

  public double getAtlag() {
    melyseg = atlagosszeg = atlagdb = 0;
    ratlag(gyoker);
    atlag = ((double) atlagosszeg) / atlagdb;
    return atlag;
  }

  public double getSzoras() {
    atlag = getAtlag();
    szorasosszeg = 0.0;
    melyseg = atlagdb = 0;

    rszoras(gyoker);

    if (atlagdb - 1 > 0) {
      szoras = Math.sqrt(szorasosszeg / (atlagdb - 1));
    } else {
      szoras = Math.sqrt(szorasosszeg);
    }

    return szoras;
  }

  public void rmelyseg(Csomopont elem) {
    if (elem != null) {
      ++melyseg;
      if (melyseg > maxMelyseg) {
        maxMelyseg = melyseg;
      }
      rmelyseg(elem.egyesGyermek());
      rmelyseg(elem.nullasGyermek());
      --melyseg;
    }
  }

  public void ratlag(Csomopont elem) {
    if (elem != null) {
      ++melyseg;
      ratlag(elem.egyesGyermek());
      ratlag(elem.nullasGyermek());
      --melyseg;
      if (elem.egyesGyermek() == null && elem.nullasGyermek() == null) {
        ++atlagdb;
        atlagosszeg += melyseg;
      }
    }
  }

  public void rszoras(Csomopont elem) {
    if (elem != null) {
      ++melyseg;
      rszoras(elem.egyesGyermek());
      rszoras(elem.nullasGyermek());
      --melyseg;
      if (elem.egyesGyermek() == null && elem.nullasGyermek() == null) {
        ++atlagdb;
        szorasosszeg += ((melyseg - atlag) * (melyseg - atlag));
      }
    }
  }


  public static void usage() {
    System.out.println("Usage: lzwtree in_file -o out_file");
  }

  

public void doGet(HttpServletRequest request, HttpServletResponse response)
         throws IOException, ServletException {
response.setContentType("text/html");
PrintWriter out = response.getWriter();


    
    String inFile ="beFile";
    
    try {

      
      LZWBinFa binFa = new LZWBinFa();

      
	byte[] b={'0','1','0','0','0','1'};
        for (int i = 0; i < 6; ++i) {
          
	if(b[i]=='0') binFa.egyBitFeldolg('1');
	else binFa.egyBitFeldolg('0');
        }

      
binFa.lement();
      out.println("<html>");
      out.println("<head><title>Hello, World</title></head>");
      out.println("<body>");
      out.println("<p>depth = " + binFa.getMelyseg()+"</p>");
      out.println("<p>mean = " + binFa.getAtlag()+"</p>");
      out.println("<p>var = " + binFa.getSzoras()+"</p>");
      
      
	szoveg[10]="asfawasd";
	for(int i=0;i<binFa.sor;i++){
	out.println("<p>"+binFa.szoveg[i].substring(4)+"</p>");
	
	}

    }  finally {
         out.close();  
    }
out.println("</body></html>");
      

  }

}
