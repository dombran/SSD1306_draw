package app;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.TouchEvent;
import org.eclipse.swt.events.TouchListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Text;

public class ssd1306_ge {
	
	public class recStruct{
		public int x;
		public int y;
		public boolean fill; 
		public int num_page;
		public int num_page_row;
		public int num_column;
		public recStruct(int x, int y) {
			this.x = x;
			this.y = y;
			this.fill = false;
			num_page = 0;
			num_page_row = 0;
			num_column = 0;
		}	
		
		public void overset(int x, int y) {
			this.x = x;
			this.y = y;
			this.fill = false;
		}	
	}
	
	
	protected Shell shell;
	private static boolean drag = false;
	private int startX, startY;
	private int endX, endY;
	private int heightGrid = 64;
	private int widthGrid = 128;
	private int gridSize = 10;
	

	
	private recStruct[] rs= new recStruct[heightGrid*widthGrid];
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Text txtNewText;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ssd1306_ge window = new ssd1306_ge();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	protected void re_set_gridXY() {
		int iii=0;
		int row=0;
		for(int j=0; j<heightGrid; j++) {
			
			for(int k=0; k<widthGrid; k++) {				
				rs[iii].overset(k*gridSize, j*gridSize);
				rs[iii].num_column = k;
				rs[iii].num_page_row = row;
				rs[iii].num_page = j/8;
				if((heightGrid*widthGrid) > iii)
					iii++;
			}		
			row++;
			if(row >= 8)
				row = 0;
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(1459 , 755);
		shell.setText("SWT Application");
		Color col = shell.getBackground();
		
				
		int iii=0;
		for(int j=0; j<heightGrid; j++)
			for(int k=0; k<widthGrid; k++) {				
				rs[iii] = new recStruct(k*gridSize, j*gridSize);
				if((heightGrid*widthGrid) > iii)
					iii++;
			}
 		
		Canvas canvas = new Canvas(shell, SWT.BORDER );
		canvas.setBounds(10, 80, gridSize*widthGrid, gridSize*heightGrid);
		
		Button buttonCodeGen = new Button(shell, SWT.NONE);
		//GC gcol = new GC(canvas);
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(10, 10, 397, 17);
		
		Button radioButton128x64 = new Button(composite, SWT.RADIO);
		radioButton128x64.setBounds(0, 0, 77, 17);
		radioButton128x64.setText("128x64");
		radioButton128x64.setSelection(true);
		radioButton128x64.setEnabled(true);
		
		Button radioButton128x32 = new Button(composite, SWT.RADIO);
		radioButton128x32.setBounds(83, 0, 75, 17);
		radioButton128x32.setText("128x32");
		
		Button radioButton32x32 = new Button(composite, SWT.RADIO);
		radioButton32x32.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				heightGrid = 32;
				widthGrid = 32;
				shell.setSize((gridSize*widthGrid) + 10 + 10 + 300, (gridSize*heightGrid) + 80 + 10 + 25);
				canvas.setBounds(10, 80, gridSize*widthGrid, gridSize*heightGrid);
				txtNewText.setBounds((gridSize*widthGrid) + 10, 80, 300, 300);
				buttonCodeGen.setBounds((gridSize*widthGrid) + 100, 40, 144, 28);
				shell.redraw();
				canvas.redraw();
				re_set_gridXY();
			}
		});
		radioButton32x32.setBounds(158, 0, 67, 17);
		formToolkit.adapt(radioButton32x32, true, true);
		radioButton32x32.setText("32x32");
		
		Button radioButton16x16 = new Button(composite, SWT.RADIO);
		radioButton16x16.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				heightGrid = 16;
				widthGrid = 16;
				shell.setSize((gridSize*widthGrid) + 10 + 10 + 300, (gridSize*heightGrid) + 80 + 10 + 25);
				canvas.setBounds(10, 80, gridSize*widthGrid, gridSize*heightGrid);
				txtNewText.setBounds((gridSize*widthGrid) + 10, 80, 300, 300);
				buttonCodeGen.setBounds((gridSize*widthGrid) + 100, 40, 144, 28);
				shell.redraw();
				canvas.redraw();
				re_set_gridXY();
			}
		});
		radioButton16x16.setBounds(231, 0, 77, 17);
		formToolkit.adapt(radioButton16x16, true, true);
		radioButton16x16.setText("16x16");
		
		Button radioButton8x8 = new Button(composite, SWT.RADIO);
		radioButton8x8.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				heightGrid = 8;
				widthGrid = 8;
				shell.setSize((gridSize*widthGrid) + 10 + 10 + 300, (gridSize*heightGrid) + 80 + 10 + 25);
				canvas.setBounds(10, 80, gridSize*widthGrid, gridSize*heightGrid);
				txtNewText.setBounds((gridSize*widthGrid) + 10, 80, 300, 300);
				buttonCodeGen.setBounds((gridSize*widthGrid) + 100, 40, 144, 28);
				shell.redraw();
				canvas.redraw();
				re_set_gridXY();
			}
		});
		radioButton8x8.setBounds(300, 0, 61, 17);
		formToolkit.adapt(radioButton8x8, true, true);
		radioButton8x8.setText("8x8");
		
		Composite composite_1 = formToolkit.createComposite(shell, SWT.NONE);
		composite_1.setBounds(10, 39, 178, 17);
		formToolkit.paintBordersFor(composite_1);
		
		Button radioButton5px = new Button(composite_1, SWT.RADIO);
		radioButton5px.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				gridSize = 5;	
				shell.setSize((gridSize*widthGrid) + 10 + 10 + 300, (gridSize*heightGrid) + 80 + 10 + 25);
				canvas.setBounds(10, 80, gridSize*widthGrid, gridSize*heightGrid);
				txtNewText.setBounds((gridSize*widthGrid) + 10, 80, 300, 300);
				shell.redraw();
				canvas.redraw();
				
				int iii=0;
				for(int j=0; j<heightGrid; j++)
					for(int k=0; k<widthGrid; k++) {				
						rs[iii].overset(k*gridSize, j*gridSize);
						if((heightGrid*widthGrid) > iii)
							iii++;
					}
			}
		});
		radioButton5px.setBounds(0, 0, 77, 17);
		formToolkit.adapt(radioButton5px, true, true);
		radioButton5px.setText("5px Grid");
		
		Button radioButton10px = new Button(composite_1, SWT.RADIO);
		radioButton10px.setSelection(true);
		radioButton10px.setEnabled(true);
		radioButton10px.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				gridSize = 10;
				shell.setSize((gridSize*widthGrid) + 10 + 10 + 300, (gridSize*heightGrid) + 80 + 10 + 25);
				canvas.setBounds(10, 80, gridSize*widthGrid, gridSize*heightGrid);
				txtNewText.setBounds((gridSize*widthGrid) + 10, 80, 300, 300);
				shell.redraw();
				canvas.redraw();
				
				int iii=0;
				for(int j=0; j<heightGrid; j++)
					for(int k=0; k<widthGrid; k++) {				
						rs[iii].overset(k*gridSize, j*gridSize);
						if((heightGrid*widthGrid) > iii)
							iii++;
					}
			}
		});
		radioButton10px.setText("10px Grid");
		radioButton10px.setBounds(84, 0, 87, 17);
		formToolkit.adapt(radioButton10px, true, true);
		
		
		
		canvas.addListener(SWT.MouseDown, new Listener() {
			public void handleEvent(Event e){
	            startY = e.y;
	            startX = e.x;
	            drag = true;
	        }
		});
		
	    canvas.addListener(SWT.MouseUp, new Listener(){
	        public void handleEvent(Event e){
	            endY = e.y;
	            endX = e.x;
	            drag = false;
	            int x = (endX / gridSize) * gridSize;
	            int y = (endY / gridSize) * gridSize;

	            for(int i=0; i<(heightGrid*widthGrid); i++) {
	            	           	
	            	if((x == rs[i].x) && (y == rs[i].y)) {
		            	if(rs[i].fill) {
	            			rs[i].fill = false;
		            	}else { 
	            			rs[i].fill = true;	                   
		            	}
		            	
		            	canvas.redraw(rs[i].x+1, rs[i].y+1, gridSize-1, gridSize-1, true);
	            		break;
	            		
	            	}
		        }
	            
	            
	        }
	    });
	    
	    canvas.addPaintListener(new PaintListener(){
	        public void paintControl(PaintEvent e){
	            Rectangle clientArea = canvas.getClientArea();
	            e.gc.setForeground(e.gc.getDevice().getSystemColor(SWT.COLOR_BLACK));
	  
	            for(int i = 0; i < clientArea.width; i += gridSize)
	            	e.gc.drawLine(i,0,i,clientArea.height);	            
	            for(int i = 0; i < clientArea.height; i += gridSize)
	            	e.gc.drawLine(0,i,clientArea.width,i);		            

		        for(int i=0; i<(heightGrid*widthGrid); i++)
	            	if(!rs[i].fill) {
	            		e.gc.setBackground(col);
	            		e.gc.fillRectangle(rs[i].x+1, rs[i].y+1, gridSize - 1, gridSize - 1);
            		} else {
            			e.gc.setBackground(e.display.getSystemColor(SWT.COLOR_RED));;
            			e.gc.fillRectangle(rs[i].x+1, rs[i].y+1, gridSize - 1, gridSize - 1);
              		}

	        }
	    });
	    
		radioButton128x64.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				heightGrid = 64;
				widthGrid = 128;
				shell.setSize((gridSize*widthGrid) + 10 + 10 + 300, (gridSize*heightGrid) + 80 + 10 + 25);
				canvas.setBounds(10, 80, gridSize*widthGrid, gridSize*heightGrid);
				txtNewText.setBounds((gridSize*widthGrid) + 10, 80, 300, 300);
				buttonCodeGen.setBounds((gridSize*widthGrid) + 10, 40, 144, 28);
				shell.redraw();
				canvas.redraw();
				re_set_gridXY();
			}
		});
		radioButton128x32.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				heightGrid = 32;
				widthGrid = 128;
				shell.setSize((gridSize*widthGrid) + 10 + 10 + 300, (gridSize*heightGrid) + 80 + 10 + 25);
				canvas.setBounds(10, 80, gridSize*widthGrid, gridSize*heightGrid);
				txtNewText.setBounds((gridSize*widthGrid) + 10, 80, 300, 300);
				buttonCodeGen.setBounds((gridSize*widthGrid) + 10, 40, 144, 28);
				shell.redraw();
				canvas.redraw();
				re_set_gridXY();
			}
		});
		
		// ============= TEXT =========================
		
		txtNewText = formToolkit.createText(shell, "New Text", SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL);
		txtNewText.setBounds((gridSize*widthGrid) + 10, 80, 300, 300);
		
		
		
		buttonCodeGen.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				txtNewText.setText("");					
				//String str = String.valueOf(k);
				
				int k=0, h=0;
				int a,b,c,d,ee,f,g,he;
				txtNewText.append("uint8_t buff[] = { \n");
				int[] mas = new int[(heightGrid/8)*widthGrid];
				for(int i=0; i<mas.length; i++) {
					if(k == widthGrid) {
						k=0;
						txtNewText.append("\n");
						h++;
					}
					a = (rs[i + (0*widthGrid) + (h*7*widthGrid)].fill ? 1 : 0);
							b= ((rs[i + (1*widthGrid) + (h*7*widthGrid)].fill ? 1 : 0)*2);
							c = ((rs[i + (2*widthGrid) + (h*7*widthGrid)].fill ? 1 : 0)*4);
							d = ((rs[i + (3*widthGrid) + (h*7*widthGrid)].fill ? 1 : 0)*8);
							ee = ((rs[i + (4*widthGrid) + (h*7*widthGrid)].fill ? 1 : 0)*16);
							f = ((rs[i + (5*widthGrid) + (h*7*widthGrid)].fill ? 1 : 0)*32);
							g = ((rs[i + (6*widthGrid) + (h*7*widthGrid)].fill ? 1 : 0)*64);
							he = ((rs[i + (7*widthGrid) + (h*7*widthGrid)].fill ? 1 : 0)*128);
					mas[i] = a+b+c+d+ee+f+g+he;
					
					k++;
					
					txtNewText.append( " 0x" + Integer.toHexString(mas[i]) );
					txtNewText.append(",");
				}
				txtNewText.append("};");
				

			}
		});
		buttonCodeGen.setBounds(1290, 40, 144, 28);
		formToolkit.adapt(buttonCodeGen, true, true);
		buttonCodeGen.setText("Генерировать код");

	}
}
