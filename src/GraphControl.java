import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;

import com.sun.opengl.util.j2d.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.lang.Math;
import java.nio.DoubleBuffer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

class doubleWrap
{
	double dNum;
}

public class GraphControl extends GLCanvas implements GLEventListener, MouseListener, MouseMotionListener
{
	public class DPoint
	{
		double x;
		double y;
	}
	
	public class DataSeries
	{
		String strName;
		Color clrLineColor;
		int iLineThickness;
		Vector<DPoint> vectDataArray;
		
		double dXMin;
		double dYMin;
		double dXMax;
		double dYMax;
		
		public DataSeries()
		{
			strName = "";
			clrLineColor = new Color(0, 0, 0);
			iLineThickness = 1;
			vectDataArray = new Vector<DPoint>();
		}
	}
	
	public class DRect
	{
		double left;
		double right;
		double bottom;
		double top;
		
		public DRect()
		{
			left = 0;
			right = 0;
			bottom = 0;
			top = 0;
		}
		
		public DRect(DRect r)
		{
			left = r.left;
			right = r.right;
			top = r.top;
			bottom = r.bottom;
		}
		
		public double getHeight()
		{
			return Math.abs(this.top - this.bottom);
		}
		
		public double getWidth()
		{
			return Math.abs(this.right - this.left);
		}
	}
	
	static double NANOSECONDS = 1;
	static double MICROSECONDS = 1000;
	static double MILLISECONDS = 1000000;
	static double SECONDS = 1000000000;
	static double MINUTES =	60 * SECONDS;
	static double HOURS = 60 * MINUTES;
	static double DAYS = 24 * HOURS;
	static double YEARS = 365 * DAYS;

	static double NANOUNITS = 1;
	static double MICROUNITS = 1000 * NANOUNITS;
	static double MILLIUNITS = 1000 * MICROUNITS;
	static double UNITS = 1000 * MILLIUNITS;
	static double KILOUNITS = 1000 * UNITS;
	static double MEGAUNITS = 1000 * KILOUNITS;

	DRect m_drectView = new DRect();
	DRect m_rectGraph = new DRect();
	double m_dXMultiplier;
	double m_dYMultiplier;
	double m_dInvXMultiplier;
	double m_dInvYMultiplier;

	int m_iMajorUnitTypeX;
	double m_dMajorUnitXTypeValue;
	double m_dNextMajorUnitXTypeValue;
	double m_dNextNextMajorUnitXTypeValue;
	String m_strXAxisLabel = "";
	String m_strXAxisLabelShort = "";
	
	int m_iMajorUnitTypeY;
	double m_dMajorUnitYTypeValue;
	double m_dNextMajorUnitYTypeValue;
	double m_dNextNextMajorUnitYTypeValue;
	String m_strYAxisLabel;
	String m_strYAxisLabelShort;

	Vector<DataSeries> m_vectDataSeries = new Vector<DataSeries>();
	
	static GLU glu = new GLU();
    
    static Font m_fontXAxisLabel = new Font("Dialog", Font.BOLD, 14);
    static Font m_fontYAxisLabel = new Font("Dialog", Font.BOLD, 14);
    static Font m_fontXAxisDivision = new Font("Dialog", Font.PLAIN, 12);  //  @jve:decl-index=0:
    static Font m_fontYAxisDivision = new Font("Dialog", Font.PLAIN, 12);

    static int JUSTIFY_LEFT = 0;
    static int JUSTIFY_CENTER = 1;
    static int JUSTIFY_RIGHT = 2;
    
    TextRenderer m_rendererXAxisLabel;
    TextRenderer m_rendererYAxisLabel;
    TextRenderer m_rendererXAxisDivision;
    TextRenderer m_rendererYAxisDivision;
    
    boolean m_bTranslating = false;
    boolean m_bZooming = false;
    boolean m_bResizing = false;
    boolean m_bZoomToolTracking = false;
    DRect m_ZoomSelRect = new DRect();
    boolean m_bLeftButtonDown = false;
    boolean m_bRightButtonDown = false;
    
	Point m_pointLastCursorPos = new Point();
	DRect m_drectLastViewPos = new DRect();
	
	int m_iMode = 0; // 0 = Pan, 1 = Zoom in, 2 = Zoom out
	Cursor m_curOpenHand;
	Cursor m_curClosedHand;
	Cursor m_curZoomIn;
	Cursor m_curZoomOut;
	boolean m_bAutoScaleX = true;
	boolean m_bAutoScaleY = true;
    private ArrayList<AutoScaleListener> _listeners = new ArrayList<AutoScaleListener>();

    private static final long serialVersionUID = 1L;

    /**
	 * This is the default constructor
	 * @param caps 
	 */
	public GraphControl(GLCapabilities caps) 
	{
		super(caps);

		setVisible(true);
        addGLEventListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
		setAutoSwapBufferMode(false);
		
		m_drectView.left = 0;
		m_drectView.top = 800 * MILLIUNITS;
		m_drectView.right = 15 * SECONDS;
		m_drectView.bottom = -800 * MILLIUNITS;
	}
	
    public void init(GLAutoDrawable drawable)
    {
        GL gl = drawable.getGL();

        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(0, 1, 0, 1, -1, 1);
        
  		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
   		gl.glEnable(GL.GL_BLEND);
   		gl.glEnable(GL.GL_LINE_SMOOTH);
   		gl.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_NICEST);
   		
   	    m_rendererXAxisLabel = new TextRenderer(m_fontXAxisLabel, true, false);
   	    m_rendererYAxisLabel = new TextRenderer(m_fontYAxisLabel, true, false);
   	    m_rendererXAxisDivision = new TextRenderer(m_fontXAxisDivision, true, false);
   	    m_rendererYAxisDivision = new TextRenderer(m_fontYAxisDivision, true, false);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image imgOpenHand = toolkit.getImage(getClass().getResource("/images/openhand.gif"));
        m_curOpenHand = toolkit.createCustomCursor(imgOpenHand, new Point(7,7), "openhand");
        Image imgClosedHand = toolkit.getImage(getClass().getResource("/images/closedhand.gif"));
        m_curClosedHand = toolkit.createCustomCursor(imgClosedHand, new Point(7,7), "closedhand");
        Image imgZoomIn = toolkit.getImage(getClass().getResource("/images/zoomin.gif"));
        m_curZoomIn = toolkit.createCustomCursor(imgZoomIn, new Point(0,0), "zoomin");
        Image imgZoomOut = toolkit.getImage(getClass().getResource("/images/zoomout.gif"));
        m_curZoomOut = toolkit.createCustomCursor(imgZoomOut, new Point(5,5), "zoomout");
        
        setCursor(m_curOpenHand);
    }
		
    public void display(GLAutoDrawable drawable)
    {
        GL gl = drawable.getGL();

        gl.glViewport(0, 0, this.getWidth(), this.getHeight());
    	gl.glMatrixMode(GL.GL_PROJECTION);
    	gl.glLoadIdentity( );
    	glu.gluOrtho2D(0, this.getWidth(), 0, this.getHeight());
    	gl.glMatrixMode(GL.GL_MODELVIEW);
    	gl.glLoadIdentity( );
    	gl.glTranslatef(0.375f, 0.375f, 0.0f);
    	
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        
    	gl.glLineWidth(1.0f);

    	//Set up a clipping region inside the graphrect
    	double[] upperXBoundPlane = {1.0, 0.0, 0.0, -m_rectGraph.left - 1};
        double[] lowerXBoundPlane = {-1.0, 0.0, 0.0, m_rectGraph.right};
        double[] upperYBoundPlane = {0.0, -1.0, 0.0, m_rectGraph.top};
        double[] lowerYBoundPlane = {0.0, 1.0, 0.0, -m_rectGraph.bottom - 1};
    	
        DrawGraph();

        gl.glClipPlane(GL.GL_CLIP_PLANE0, DoubleBuffer.wrap(upperXBoundPlane));
    	gl.glEnable(GL.GL_CLIP_PLANE0);
    	gl.glClipPlane(GL.GL_CLIP_PLANE1, DoubleBuffer.wrap(lowerXBoundPlane));
    	gl.glEnable(GL.GL_CLIP_PLANE1);
    	gl.glClipPlane(GL.GL_CLIP_PLANE2, DoubleBuffer.wrap(upperYBoundPlane));
    	gl.glEnable(GL.GL_CLIP_PLANE2);
    	gl.glClipPlane(GL.GL_CLIP_PLANE3, DoubleBuffer.wrap(lowerYBoundPlane));
    	gl.glEnable(GL.GL_CLIP_PLANE3);

        DrawChannelLines();
    	DrawZoomBox();
        
    	//Remove the clipping region
    	gl.glDisable(GL.GL_CLIP_PLANE0);
    	gl.glDisable(GL.GL_CLIP_PLANE1);
    	gl.glDisable(GL.GL_CLIP_PLANE2);
    	gl.glDisable(GL.GL_CLIP_PLANE3);
        
    	
        this.swapBuffers();
    }
    
    /**
     * Called by the drawable when the surface resizes itself. Used to
     * reset the viewport dimensions.
     *
     * @param drawable The display context to render to
     */
    public void reshape(GLAutoDrawable drawable,
                        int x,
                        int y,
                        int width,
                        int height)
    {
    }

    protected void processMouseEvent(MouseEvent e)
    {
    	double MINWIDTH = 1;
    	double MINHEIGHT = 20;

    	if (e.getButton() == MouseEvent.BUTTON1)
    	{
    		if (e.getID() == MouseEvent.MOUSE_PRESSED)
    		{
    			// The left mouse button was just pressed down
    			m_bLeftButtonDown = true;
    			
    			if (this.m_iMode == 0)
    			{
	    			if (!m_bTranslating && !m_bZooming && !m_bResizing)
	    			{
	    				m_pointLastCursorPos = e.getPoint();
	
	    				m_drectLastViewPos.top = m_drectView.top;
	    				m_drectLastViewPos.bottom = m_drectView.bottom;
	    				m_drectLastViewPos.left = m_drectView.left;
	    				m_drectLastViewPos.right = m_drectView.right;
	    				
	    				m_bTranslating = true;
	    			}
    			}
    			else if (this.m_iMode == 1)
    			{
    				if (m_bZoomToolTracking == false)
    					m_bZoomToolTracking = true;
    				
    				m_ZoomSelRect.left = e.getPoint().x;
    				m_ZoomSelRect.right = e.getPoint().x;
    				
    				int iWindowHeight = this.getHeight();
    				m_ZoomSelRect.top = iWindowHeight - e.getPoint().y;
    				m_ZoomSelRect.bottom = iWindowHeight - e.getPoint().y;
    			}
    			else if (this.m_iMode == 2)
    			{
    				
    			}
    		}
    		else if (e.getID() == MouseEvent.MOUSE_RELEASED) // Left mouse button released
    		{
    			// The left mouse button was just released
    			m_bLeftButtonDown = false;
    			
    			if (m_iMode == 0)
    			{
    				m_bTranslating = false;
    			}
    			else if (m_iMode == 1)
    			{
    				this.turnOffAutoScale();
    				
    				m_bZoomToolTracking = false;
    				// Zoom in
    				if ((m_ZoomSelRect.right == m_ZoomSelRect.left)
    					|| (m_ZoomSelRect.top == m_ZoomSelRect.bottom))
    				{
						//A one-click zoom
						DRect dNewViewRect = new DRect();

						double dWidth = m_drectView.right - m_drectView.left;
						double dHeight = m_drectView.top - m_drectView.bottom;

						double dMouseXPos = m_drectView.left + ((double)(m_ZoomSelRect.left - m_rectGraph.left) * m_dXMultiplier);
						double dMouseYPos = m_drectView.bottom + ((double)(m_ZoomSelRect.bottom - m_rectGraph.bottom) * m_dYMultiplier);
						
						if (dWidth * 0.5 > MINWIDTH)
						{
							dNewViewRect.left = dMouseXPos - (dWidth / 4);
							dNewViewRect.right = dMouseXPos + (dWidth / 4);
						}
						else
						{
							dNewViewRect.left = dMouseXPos - (MINWIDTH / 2);
							dNewViewRect.right = dMouseXPos + (MINWIDTH / 2);

							//Make sure that we don't run off the left or right
							if (dNewViewRect.right > 104 * DAYS)
							{
								dNewViewRect.right = 104 * DAYS;
								dNewViewRect.left = dNewViewRect.right - 1;
							}
							if (dNewViewRect.left < -104 * DAYS)
							{
								dNewViewRect.left = -104 * DAYS;
								dNewViewRect.left = dNewViewRect.left + 1;
							}
						}

						if (dHeight * 0.5 > MINHEIGHT)
						{
							dNewViewRect.top = dMouseYPos + (dHeight / 4);
							dNewViewRect.bottom = dMouseYPos - (dHeight / 4);
						}
						else
						{
							dNewViewRect.top = dMouseYPos + (MINHEIGHT / 2);
							dNewViewRect.bottom = dMouseYPos - (MINHEIGHT / 2);

							//Make sure that we don't run off the top or bottom
							if (dNewViewRect.top > 9 * MEGAUNITS)
							{
								dNewViewRect.top = 9 * MEGAUNITS;
								dNewViewRect.bottom = dNewViewRect.top - 1;
							}
							if (dNewViewRect.bottom < -9 * MEGAUNITS)
							{
								dNewViewRect.bottom = -9 * MEGAUNITS;
								dNewViewRect.top = dNewViewRect.bottom + 1;
							}
						}

						m_drectView.left = dNewViewRect.left;
						m_drectView.right = dNewViewRect.right;
						m_drectView.top = dNewViewRect.top;
						m_drectView.bottom = dNewViewRect.bottom;
						
    					this.repaint();
					}
    				else
    				{
    					//Create a normalized rect out of m_ZoomSelRect
    					DRect normalizedRect = new DRect();
    					DRect dNewViewRect = new DRect();

    					if (m_ZoomSelRect.top > m_ZoomSelRect.bottom)
    					{
    						normalizedRect.top = m_ZoomSelRect.top;
    						normalizedRect.bottom = m_ZoomSelRect.bottom;
    					}
    					else
    					{
    						normalizedRect.top = m_ZoomSelRect.bottom;
    						normalizedRect.bottom = m_ZoomSelRect.top;
    					}

    					if (m_ZoomSelRect.right > m_ZoomSelRect.left)
    					{
    						normalizedRect.right = m_ZoomSelRect.right;
    						normalizedRect.left = m_ZoomSelRect.left;
    					}
    					else
    					{
    						normalizedRect.right = m_ZoomSelRect.left;
    						normalizedRect.left = m_ZoomSelRect.right;
    					}

    					dNewViewRect.left = m_drectView.left + ((normalizedRect.left - m_rectGraph.left) * m_dXMultiplier);
    					dNewViewRect.right = m_drectView.left + ((normalizedRect.right - m_rectGraph.left) * m_dXMultiplier);
    					dNewViewRect.top = m_drectView.bottom + ((normalizedRect.top - m_rectGraph.bottom) * m_dYMultiplier);
    					dNewViewRect.bottom = m_drectView.bottom + ((normalizedRect.bottom - m_rectGraph.bottom) * m_dYMultiplier);
    					
    					if (dNewViewRect.getWidth() < MINWIDTH)
    					{
    						double centerx = dNewViewRect.left + (dNewViewRect.getWidth() / 2);
    						dNewViewRect.left = centerx - (MINWIDTH * 0.5);
    						dNewViewRect.right = dNewViewRect.left + MINWIDTH;
    					}
    					if (dNewViewRect.getHeight() < MINHEIGHT)
    					{
    						double centery = dNewViewRect.bottom + (dNewViewRect.getHeight() / 2);
    						dNewViewRect.bottom = centery - (MINHEIGHT * 0.5);
    						dNewViewRect.top = dNewViewRect.bottom + MINHEIGHT;
    					}
    					
    					double dWidth = dNewViewRect.getWidth();
    					double dHeight = dNewViewRect.getHeight();

    					//Make sure that we don't run off the left or right
    					if (dNewViewRect.right > 104 * DAYS)
    					{
    						dNewViewRect.right = 104 * DAYS;
    						dNewViewRect.left = dNewViewRect.right - dWidth;
    					}
    					if (dNewViewRect.left < -104 * DAYS)
    					{
    						dNewViewRect.left = -104 * DAYS;
    						dNewViewRect.left = dNewViewRect.left + dWidth;
    					}
    					//Make sure that we don't run off the top or bottom
    					if (dNewViewRect.top > 9 * MEGAUNITS)
    					{
    						dNewViewRect.top = 9 * MEGAUNITS;
    						dNewViewRect.bottom = dNewViewRect.top - dHeight;
    					}
    					if (dNewViewRect.bottom < -9 * MEGAUNITS)
    					{
    						dNewViewRect.bottom = -9 * MEGAUNITS;
    						dNewViewRect.top = dNewViewRect.bottom + dHeight;
    					}

    					//Set the new viewrect to the m_ZoomSelRect
    					m_drectView.left = dNewViewRect.left;
    					m_drectView.right = dNewViewRect.right;
    					m_drectView.top = dNewViewRect.top;
    					m_drectView.bottom = dNewViewRect.bottom;
    					
    					this.repaint();
    				}
    			}
    			else if (m_iMode == 2) // Zoom out
    			{
					this.turnOffAutoScale();

					//A one-click zoom
					DRect dNewViewRect = new DRect();
					
					double dWidth = m_drectView.right - m_drectView.left;
					double dHeight = m_drectView.top - m_drectView.bottom;

					int iWindowHeight = this.getHeight();
					
					double dMouseXPos = m_drectView.left + ((double)(e.getPoint().x - m_rectGraph.left) * m_dXMultiplier);
					double dMouseYPos = m_drectView.bottom + ((double)((iWindowHeight - e.getPoint().y) - m_rectGraph.bottom) * m_dYMultiplier);
					
					dNewViewRect.left = dMouseXPos - dWidth;
					dNewViewRect.right = dMouseXPos + dWidth;
					dNewViewRect.top = dMouseYPos + dHeight;
					dNewViewRect.bottom = dMouseYPos - dHeight;

					if (dNewViewRect.left < -104 * DAYS)
						dNewViewRect.left = -104 * DAYS;
						
					if (dNewViewRect.right > 104 * DAYS)
						dNewViewRect.right = 104 * DAYS;

					if (dNewViewRect.bottom < -9 * MEGAUNITS)
						dNewViewRect.bottom = -9 * MEGAUNITS;

					if (dNewViewRect.top > 9 * MEGAUNITS)
						dNewViewRect.top = 9 * MEGAUNITS;

					//Set the new viewrect to the m_ZoomSelRect
					m_drectView.left = dNewViewRect.left;
					m_drectView.right = dNewViewRect.right;
					m_drectView.top = dNewViewRect.top;
					m_drectView.bottom = dNewViewRect.bottom;
					
					this.repaint();
    			}
    		}
    	}
    	else if (e.getButton() == MouseEvent.BUTTON3)
    	{
    		if (e.getID() == MouseEvent.MOUSE_PRESSED)
    		{
    			// The right mouse button was just pressed down
    			m_bRightButtonDown = true;
    			
    			if (this.m_iMode == 0)
    			{
	    			if (!m_bZooming && !m_bTranslating && !m_bResizing)
	    			{
	    				m_pointLastCursorPos = e.getPoint();
	    				//m_rectLastViewPos = m_rectView;
	     				m_drectLastViewPos.top = m_drectView.top;
	    				m_drectLastViewPos.bottom = m_drectView.bottom;
	    				m_drectLastViewPos.left = m_drectView.left;
	    				m_drectLastViewPos.right = m_drectView.right;
	       				
	        			if (e.isShiftDown())
	        				m_bResizing = true;
	        			else
	        				m_bZooming = true;
	        				
	    			}
    			}
    		}
    		else
    		{
	    		if (e.getID() == MouseEvent.MOUSE_RELEASED) // Right mouse button released
	    		{
	    			// The right mouse button was just released
	    			m_bRightButtonDown = false;
	    			
	    			m_bZooming = false;
	    			m_bResizing = false;
	    		}
    		}
    	}
    	
    	super.processMouseEvent(e);
    }
    
    protected void turnOnAutoScaleX()
    {
    	if (this.m_bAutoScaleX == false)
    	{
    		this.m_bAutoScaleX = true;
    		fireAutoScaleChangedEvent();
    	}
    }
    
    protected void turnOnAutoScaleY()
    {
    	if (this.m_bAutoScaleY == false)
    	{
    		this.m_bAutoScaleY = true;
    		fireAutoScaleChangedEvent();
    	}
    }

    protected void turnOffAutoScale()
    {
    	if (this.m_bAutoScaleX == true || this.m_bAutoScaleY == true)
    	{
    		this.m_bAutoScaleX = false;
    		this.m_bAutoScaleY = false;
    		fireAutoScaleChangedEvent(); 
    	}
    }
    
    protected void processMouseMotionEvent(MouseEvent e)
    {
    	Point pointCursor = e.getPoint();
    	
    	if (this.m_iMode == 0)
    	{
			// For left mouse button down, translate the graph around
			if (m_bTranslating)
			{
				//If gets to an edge, no more translation
				//Time max = +/- 104 days
				//Potential max = +/- 9 megaunits
				this.turnOffAutoScale();
				
				if ((m_drectLastViewPos.top + ((double)(pointCursor.y - m_pointLastCursorPos.y) * m_dYMultiplier)) <= 9 * MEGAUNITS
					&& ((m_drectLastViewPos.top + ((double)(pointCursor.y - m_pointLastCursorPos.y) * m_dYMultiplier)) - m_drectLastViewPos.getHeight()) >= -9 * MEGAUNITS)
				{
					m_drectView.top = m_drectLastViewPos.top + (m_dYMultiplier * (double)(pointCursor.y - m_pointLastCursorPos.y));
					m_drectView.bottom = m_drectView.top - m_drectLastViewPos.getHeight();
				}
				else if ((m_drectLastViewPos.top + ((double)(pointCursor.y - m_pointLastCursorPos.y) * m_dYMultiplier)) > 9 * MEGAUNITS)
				{
					m_drectView.top = MEGAUNITS * 9;
					m_drectView.bottom = m_drectView.top - m_drectLastViewPos.getHeight();
				}
				else if (((m_drectLastViewPos.top + ((double)(pointCursor.y - m_pointLastCursorPos.y) * m_dYMultiplier)) - m_drectLastViewPos.getHeight()) < -9 * MEGAUNITS)
				{
					m_drectView.bottom = -9 * MEGAUNITS;
					m_drectView.top = m_drectView.bottom + m_drectLastViewPos.getHeight();
				}
	
				if ((m_drectLastViewPos.left - ((double)(pointCursor.x - m_pointLastCursorPos.x) * m_dXMultiplier)) >= -104 * DAYS
						&& ((m_drectLastViewPos.left - ((double)(pointCursor.x - m_pointLastCursorPos.x) * m_dXMultiplier)) + m_drectLastViewPos.getWidth()) <= 104 * DAYS)
				{
					m_drectView.left = m_drectLastViewPos.left - (m_dXMultiplier * (double)(pointCursor.x - m_pointLastCursorPos.x));
					m_drectView.right = m_drectView.left + m_drectLastViewPos.getWidth();
				}
				else if ((m_drectLastViewPos.left - ((double)(pointCursor.x - m_pointLastCursorPos.x) * m_dXMultiplier)) < -104 * DAYS)
				{
					m_drectView.left = -104 * DAYS;
					m_drectView.right = m_drectView.left + m_drectLastViewPos.getWidth();
				}
				else if ((m_drectLastViewPos.left - ((double)(pointCursor.x - m_pointLastCursorPos.x) * m_dXMultiplier)) + m_drectLastViewPos.getWidth() > 104 * DAYS)
				{
					m_drectView.right = 104 * DAYS;
					m_drectView.left = m_drectView.right - m_drectLastViewPos.getWidth();
				}
				
		    	this.repaint();
			}
			else if (m_bZooming)
			{
				double dfactor = Math.pow(1.05,(double)(m_pointLastCursorPos.y - pointCursor.y));
				double MINWIDTH = 1;
				double MINHEIGHT = 20;
				
				this.turnOffAutoScale();
				
				if ((m_drectLastViewPos.right - (m_drectLastViewPos.getWidth() * 0.5) + 
					(m_drectLastViewPos.getWidth() * dfactor) * 0.5) - 
					(m_drectLastViewPos.left + (m_drectLastViewPos.getWidth() * 0.5) - 
					(m_drectLastViewPos.getWidth() * dfactor) * 0.5) > MINWIDTH)
				{
					if (m_drectLastViewPos.left + (m_drectLastViewPos.getWidth() * 0.5) - (m_drectLastViewPos.getWidth() * dfactor) * 0.5 < -104 * DAYS)
						m_drectView.left = -104 * DAYS;
					else
						m_drectView.left = m_drectLastViewPos.left + (m_drectLastViewPos.getWidth() * 0.5) - (m_drectLastViewPos.getWidth() * dfactor) * 0.5;
	
					if (m_drectLastViewPos.right - (m_drectLastViewPos.getWidth() * 0.5) + (m_drectLastViewPos.getWidth() * dfactor) * 0.5 > 104 * DAYS)
						m_drectView.right =  104 * DAYS;
					else
						m_drectView.right = m_drectLastViewPos.right - (m_drectLastViewPos.getWidth() * 0.5) + (m_drectLastViewPos.getWidth() * dfactor) * 0.5;
				}
				else
				{
					double centerh = m_drectView.left + ((m_drectView.right - m_drectView.left) * 0.5);			
					m_drectView.left = centerh - (MINWIDTH * 0.5);
					m_drectView.right = centerh + (MINWIDTH * 0.5);
				}
	
				if ((m_drectLastViewPos.top - (m_drectLastViewPos.getHeight() * 0.5) + 
						(m_drectLastViewPos.getHeight() * dfactor) * 0.5) - 
						(m_drectLastViewPos.bottom + (m_drectLastViewPos.getHeight() * 0.5) - 
						(m_drectLastViewPos.getHeight() * dfactor) * 0.5) > MINHEIGHT)
				{
					if (m_drectLastViewPos.top - (m_drectLastViewPos.getHeight() * 0.5) + (m_drectLastViewPos.getHeight() * dfactor) * 0.5 > 9 * MEGAUNITS)
						m_drectView.top = 9 * MEGAUNITS;
					else
						m_drectView.top = m_drectLastViewPos.top - (m_drectLastViewPos.getHeight() * 0.5) + (m_drectLastViewPos.getHeight() * dfactor) * 0.5;
					
					if (m_drectLastViewPos.bottom + (m_drectLastViewPos.getHeight() * 0.5) - (m_drectLastViewPos.getHeight() * dfactor) * 0.5 < -9 * MEGAUNITS)
						m_drectView.bottom = -9 * MEGAUNITS;
					else
						m_drectView.bottom = m_drectLastViewPos.bottom + (m_drectLastViewPos.getHeight() * 0.5) - (m_drectLastViewPos.getHeight() * dfactor) * 0.5;
				}
				else
				{
					double centerv = m_drectView.bottom + ((m_drectView.top - m_drectView.bottom) * 0.5);			
					m_drectView.top = centerv + (MINHEIGHT * 0.5);
					m_drectView.bottom = centerv - (MINHEIGHT * 0.5);
				}
				
		    	this.repaint();
			}
			else if (m_bResizing)
			{
				double dfactory = Math.pow(1.05,(double)(m_pointLastCursorPos.y - pointCursor.y));
				double dfactorx = Math.pow(1.05,(double)(m_pointLastCursorPos.x - pointCursor.x));
				double MINWIDTH = 1;
				double MINHEIGHT = 20;
	
				this.turnOffAutoScale();
				
				if ((m_drectLastViewPos.right - (m_drectLastViewPos.getWidth() * 0.5) + 
						(m_drectLastViewPos.getWidth() * dfactorx) * 0.5) - 
						(m_drectLastViewPos.left + (m_drectLastViewPos.getWidth() * 0.5) - 
						(m_drectLastViewPos.getWidth() * dfactorx) * 0.5) > MINWIDTH)
				{
					if (m_drectLastViewPos.left + (m_drectLastViewPos.getWidth() * 0.5) - (m_drectLastViewPos.getWidth() * dfactorx) * 0.5 < -104 * DAYS)
						m_drectView.left = -104 * DAYS;
					else
						m_drectView.left = m_drectLastViewPos.left + (m_drectLastViewPos.getWidth() * 0.5) - (m_drectLastViewPos.getWidth() * dfactorx) * 0.5;
	
					if (m_drectLastViewPos.right - (m_drectLastViewPos.getWidth() * 0.5) + (m_drectLastViewPos.getWidth() * dfactorx) * 0.5 > 104 * DAYS)
						m_drectView.right =  104 * DAYS;
					else
						m_drectView.right = m_drectLastViewPos.right - (m_drectLastViewPos.getWidth() * 0.5) + (m_drectLastViewPos.getWidth() * dfactorx) * 0.5;
				}
				else
				{
					double centerh = m_drectView.left + ((m_drectView.right - m_drectView.left) * 0.5);			
					m_drectView.left = centerh - (MINWIDTH * 0.5);
					m_drectView.right = centerh + (MINWIDTH * 0.5);
				}
				
				if ((m_drectLastViewPos.top - (m_drectLastViewPos.getHeight() * 0.5) + 
						(m_drectLastViewPos.getHeight() * dfactory) * 0.5) - 
						(m_drectLastViewPos.bottom + (m_drectLastViewPos.getHeight() * 0.5) - 
						(m_drectLastViewPos.getHeight() * dfactory) * 0.5) > MINHEIGHT)
				{
					if (m_drectLastViewPos.top - (m_drectLastViewPos.getHeight() * 0.5) + (m_drectLastViewPos.getHeight() * dfactory) * 0.5 > 9 * MEGAUNITS)
						m_drectView.top = 9 * MEGAUNITS;
					else
						m_drectView.top = m_drectLastViewPos.top - (m_drectLastViewPos.getHeight() * 0.5) + (m_drectLastViewPos.getHeight() * dfactory) * 0.5;
					
					if (m_drectLastViewPos.bottom + (m_drectLastViewPos.getHeight() * 0.5) - (m_drectLastViewPos.getHeight() * dfactory) * 0.5 < -9 * MEGAUNITS)
						m_drectView.bottom = -9 * MEGAUNITS;
					else
						m_drectView.bottom = m_drectLastViewPos.bottom + (m_drectLastViewPos.getHeight() * 0.5) - (m_drectLastViewPos.getHeight() * dfactory) * 0.5;
				}
				else
				{
					double centerv = m_drectView.bottom + ((m_drectView.top - m_drectView.bottom) * 0.5);			
					m_drectView.top = centerv + (MINHEIGHT * 0.5);
					m_drectView.bottom = centerv - (MINHEIGHT * 0.5);
				}
				
		    	this.repaint();
			}
    	}
    	else if (this.m_iMode == 1) // Zoom in mode
    	{
			if (m_bZoomToolTracking == true)
			{
				m_ZoomSelRect.right = (double)pointCursor.x;
								
				int iWindowHeight = this.getHeight();
				m_ZoomSelRect.bottom = iWindowHeight - pointCursor.y;
				
				this.repaint();
			}
    	}
    	else if (this.m_iMode == 2) // Zoom out mode
    	{
    		// Do nothing on mouse move for zoom out.
    	}
		
    	m_pointLastCursorPos.x = e.getPoint().x;
    	m_pointLastCursorPos.y = e.getPoint().y;
    	
    	m_drectLastViewPos.top = m_drectView.top;
    	m_drectLastViewPos.bottom = m_drectView.bottom;
       	m_drectLastViewPos.left = m_drectView.left;
    	m_drectLastViewPos.right = m_drectView.right;
    	
    	super.processMouseMotionEvent(e);
    }

	/**
     * Called by the drawable when the display mode or the display device
     * associated with the GLDrawable has changed
     */
    public void displayChanged(GLAutoDrawable drawable,
                               boolean modeChanged,
                               boolean deviceChanged)
    {
    }

    public void DrawGraph()
    {
    	GL gl = this.getGL();
    	
    	int MINOR_LINE_LENGTH = 2;
    	int MAJOR_LINE_LENGTH = 6;
    	int GREAT_LINE_LENGTH;

    	String strNextXAxisLabel = "";
    	String strNextYAxisLabel = "";

    	DRect rectWindow = new DRect();
    	rectWindow.bottom = 0;
    	rectWindow.top = this.getHeight();
    	rectWindow.left = 0;
    	rectWindow.right = this.getWidth();
    	
    	Graphics graphics = this.getGraphics();
    	
    	FontMetrics metricsXAxisLabel = graphics.getFontMetrics(m_fontYAxisLabel);
    	FontMetrics metricsYAxisLabel = graphics.getFontMetrics(m_fontXAxisLabel);
    	FontMetrics metricsXAxisDivision = graphics.getFontMetrics(m_fontXAxisDivision);
    	FontMetrics metricsYAxisDivision = graphics.getFontMetrics(m_fontYAxisDivision);

    	GREAT_LINE_LENGTH = (int)(MAJOR_LINE_LENGTH + metricsXAxisDivision.getAscent());

    	DecimalFormat decformat = new DecimalFormat("#.#");

    	// Calculate the rectangle for the graph itself
    	m_rectGraph.left = rectWindow.left + 2 + metricsYAxisLabel.getHeight() + (2 * metricsYAxisDivision.getHeight()) + GREAT_LINE_LENGTH;
    	m_rectGraph.bottom = rectWindow.bottom + (3 * metricsXAxisDivision.getHeight()) + 2 + GREAT_LINE_LENGTH;
    	m_rectGraph.right = rectWindow.right - 5;
    	m_rectGraph.top = rectWindow.top - 5;
    
    	//Calculate the multipliers (in units/pixel)
    	m_dXMultiplier = (m_drectView.right - m_drectView.left) / (m_rectGraph.right - m_rectGraph.left);
    	m_dYMultiplier = (m_drectView.top - m_drectView.bottom) / (m_rectGraph.top - m_rectGraph.bottom);
    	m_dInvXMultiplier = 1 / m_dXMultiplier;
    	m_dInvYMultiplier = 1 / m_dYMultiplier;

    	double dFrameRefX = m_dXMultiplier * 20;
    	
    	double dDifferenceX = m_drectView.right - m_drectView.left;

    	double dMajorUnitX = 0;
    	if (dFrameRefX <= 50 * NANOSECONDS) //Is the 11 pixel graduation less than 0.1 us?
    	{	//Check nanoseconds
    		if (1 >= dFrameRefX)
    			dMajorUnitX = 1; // 1 ns
    		else if (2 >= dFrameRefX)
    			dMajorUnitX = 2; // 2 ns
    		else if (5 >= dFrameRefX)
    			dMajorUnitX = 5; // 5 ns
    		else if (10 >= dFrameRefX)
    			dMajorUnitX = 10; // 10 ns
    		else if (20 >= dFrameRefX)
    			dMajorUnitX = 20; // 20 ns
    		else
    			dMajorUnitX = 50; // 50 ns

    		m_iMajorUnitTypeX = 1;
    		m_dMajorUnitXTypeValue = 1;
    		m_dNextMajorUnitXTypeValue = MICROSECONDS;
    		m_dNextNextMajorUnitXTypeValue = MILLISECONDS;
    		m_strXAxisLabel = "nanosec";
    		m_strXAxisLabelShort = "ns";
    		strNextXAxisLabel = '\u03BC' + "s"; //character 0x03BC was replaced with mu
    	}
    	else if (dFrameRefX <= 50 * MICROSECONDS) //Is the 11 pixel graduation less than 0.1 ms?
    	{	//Check microseconds
    		if (0.1 * MICROSECONDS >= dFrameRefX)
    			dMajorUnitX = 0.1 * MICROSECONDS; //0.1 us
    		else if (0.2 * MICROSECONDS >= dFrameRefX)
    			dMajorUnitX = 0.2 * MICROSECONDS; //0.2 us
    		else if (0.5 * MICROSECONDS >= dFrameRefX)
    			dMajorUnitX = 0.5 * MICROSECONDS; //0.5 us
    		else if (1 * MICROSECONDS >= dFrameRefX)
    			dMajorUnitX = 1 * MICROSECONDS; //1 us
    		else if (2 * MICROSECONDS >= dFrameRefX)
    			dMajorUnitX = 2 * MICROSECONDS; //2 us
    		else if (5 * MICROSECONDS >= dFrameRefX)
    			dMajorUnitX = 5 * MICROSECONDS; //5 us
    		else if (10 * MICROSECONDS >= dFrameRefX)
    			dMajorUnitX = 10 * MICROSECONDS; //10 us
    		else if (20 * MICROSECONDS >= dFrameRefX)
    			dMajorUnitX = 20 * MICROSECONDS; //20 us
    		else
    			dMajorUnitX = 50 * MICROSECONDS; //50 us

    		m_iMajorUnitTypeX = 2;
    		m_dMajorUnitXTypeValue = MICROSECONDS;
    		m_dNextMajorUnitXTypeValue = MILLISECONDS;
    		m_dNextNextMajorUnitXTypeValue = SECONDS;
    		m_strXAxisLabel = "microsec";
    		m_strXAxisLabelShort = '\u03BC' + "s"; //character 0x03BC was replaced with mu
    		strNextXAxisLabel = "ms";
    	}
    	else if (dFrameRefX <= 50 * MILLISECONDS) //Is the 11 pixel graduation less than 0.1 s?
    	{	//Check milliseconds
    		if (0.1 * MILLISECONDS >= dFrameRefX)
    			dMajorUnitX = 0.1 * MILLISECONDS; //0.1 ms
    		else if (0.2 * MILLISECONDS >= dFrameRefX)
    			dMajorUnitX = 0.2 * MILLISECONDS; //0.2 ms
    		else if (0.5 * MILLISECONDS >= dFrameRefX)
    			dMajorUnitX = 0.5 * MILLISECONDS; //0.5 ms
    		else if (1 * MILLISECONDS >= dFrameRefX)
    			dMajorUnitX = 1 * MILLISECONDS; //1 ms
    		else if (2 * MILLISECONDS >= dFrameRefX)
    			dMajorUnitX = 2 * MILLISECONDS; //2 ms
    		else if (5 * MILLISECONDS >= dFrameRefX)
    			dMajorUnitX = 5 * MILLISECONDS; //5 ms
    		else if (10 * MILLISECONDS >= dFrameRefX)
    			dMajorUnitX = 10 * MILLISECONDS; //10 ms
    		else if (20 * MILLISECONDS >= dFrameRefX)
    			dMajorUnitX = 20 * MILLISECONDS; //20 ms
    		else
    			dMajorUnitX = 50 * MILLISECONDS; //50 ms

    		m_iMajorUnitTypeX = 3;
    		m_dMajorUnitXTypeValue = MILLISECONDS;
    		m_dNextMajorUnitXTypeValue = SECONDS;
    		m_dNextNextMajorUnitXTypeValue = MINUTES;
    		m_strXAxisLabel = "millisec";
    		m_strXAxisLabelShort = "ms";
    		strNextXAxisLabel = "s";
    	}
    	else if (dFrameRefX <= 5 * SECONDS) //Is the 11 pixel graduation less than 0.2 min?
    	{	//Check seconds
    		if (0.1 * SECONDS >= dFrameRefX)
    			dMajorUnitX = 0.1 * SECONDS; //0.1 s
    		else if (0.2 * SECONDS >= dFrameRefX)
    			dMajorUnitX = 0.2 * SECONDS; //0.2 s
    		else if (0.5 * SECONDS >= dFrameRefX)
    			dMajorUnitX = 0.5 * SECONDS; //0.5 s
    		else if (1 * SECONDS >= dFrameRefX)
    			dMajorUnitX = 1 * SECONDS; //1 s
    		else if (2 * SECONDS >= dFrameRefX)
    			dMajorUnitX = 2 * SECONDS; //2 s
    		else
    			dMajorUnitX = 5 * SECONDS; //5 s

    		m_iMajorUnitTypeX = 4;
    		m_dMajorUnitXTypeValue = SECONDS;
    		m_dNextMajorUnitXTypeValue = MINUTES;
    		m_dNextNextMajorUnitXTypeValue = HOURS;
    		m_strXAxisLabel = "sec";
    		m_strXAxisLabelShort = "s";
    		strNextXAxisLabel = "min";
    	}
    	else if (dFrameRefX <= 5 * MINUTES) //Is the 21 pixel graduation less than 0.2 hr?
    	{	//Check minutes
    		if (0.2 * MINUTES >= dFrameRefX)
    			dMajorUnitX = 0.2 * MINUTES; //0.2 min
    		else if (0.5 * MINUTES >= dFrameRefX)
    			dMajorUnitX = 0.5 * MINUTES; //0.5 min
    		else if (1 * MINUTES >= dFrameRefX)
    			dMajorUnitX = 1 * MINUTES; //1 min
    		else if (2 * MINUTES >= dFrameRefX)
    			dMajorUnitX = 2 * MINUTES; //2 min
    		else
    			dMajorUnitX = 5 * MINUTES; //5 min

    		m_iMajorUnitTypeX = 5;
    		m_dMajorUnitXTypeValue = MINUTES;
    		m_dNextMajorUnitXTypeValue = HOURS;
    		m_dNextNextMajorUnitXTypeValue = DAYS;
    		m_strXAxisLabel = "min";
    		m_strXAxisLabelShort = "min";
    		strNextXAxisLabel = "hr";
    	}
    	else if (dFrameRefX <= 2 * HOURS) //Is the 10 pixel gradiation less than 0.2 day?
    	{	//Check hours
    		if (0.2 * HOURS >= dFrameRefX)
    			dMajorUnitX = 0.2 * HOURS; //0.2 hours
    		else if (0.5 * HOURS >= dFrameRefX)
    			dMajorUnitX = 0.5 * HOURS; //0.5 hours
    		else if (1 * HOURS >= dFrameRefX)
    			dMajorUnitX = 1 * HOURS; //1 hours
    		else
    			dMajorUnitX = 2 * HOURS; //2 hours

    		m_iMajorUnitTypeX = 6;
    		m_dMajorUnitXTypeValue = HOURS;
    		m_dNextMajorUnitXTypeValue = DAYS;
    		m_dNextNextMajorUnitXTypeValue = YEARS;
    		m_strXAxisLabel = "hour";
    		m_strXAxisLabelShort = "hr";
    		strNextXAxisLabel = "day";
    	}
    	else if (dFrameRefX <= 73 * DAYS) //Is the 10 pixel graduation less than 0.5 year?
    	{	//Check days
    		if (0.2 * DAYS >= dFrameRefX)
    			dMajorUnitX = 0.2 * DAYS;//0.2 days
    		else if (0.5 * DAYS >= dFrameRefX)
    			dMajorUnitX = 0.5 * DAYS;//0.5 days
    		else if (1 * DAYS >= dFrameRefX)
    			dMajorUnitX = 1 * DAYS;//1 days
    		else if (5 * DAYS >= dFrameRefX)
    			dMajorUnitX = 5 * DAYS;//5 days
    		else
    			dMajorUnitX = 73 * DAYS;//73 days

    		m_iMajorUnitTypeX = 7;
    		m_dMajorUnitXTypeValue = DAYS;
    		m_dNextMajorUnitXTypeValue = YEARS;
    		m_strXAxisLabel = "day";
    		m_strXAxisLabelShort = "day";
    		strNextXAxisLabel = "yr";
    	}
    	else
    	{	//Check years
    		if (0.5 * YEARS >= dFrameRefX)
    			dMajorUnitX = 0.5 * YEARS; //0.5 years
    		else if (1 * YEARS >= dFrameRefX)
    			dMajorUnitX = 1 * YEARS; //1 years
    		else if (2 * YEARS >= dFrameRefX)
    			dMajorUnitX = 2 * YEARS; //2 years
    		else if (5 * YEARS >= dFrameRefX)
    			dMajorUnitX = 5 * YEARS; //5 years
    		else if (10 * YEARS >= dFrameRefX)
    			dMajorUnitX = 10 * YEARS; //10 years
    		else if (20 * YEARS >= dFrameRefX)
    			dMajorUnitX = 20 * YEARS; //20 years
    		else
    			dMajorUnitX = 50 * YEARS; //50 years

    		m_iMajorUnitTypeX = 8;
    		m_dMajorUnitXTypeValue = YEARS;
    		m_strXAxisLabel = "year";
    		m_strXAxisLabelShort = "yr";
    	}

    	int iNumXDivisions = (int)(dDifferenceX / dMajorUnitX) + 2;

    	int i, j;

    	double xstart;

    	//Find the left side start point
    	if ((m_drectView.left / dMajorUnitX) <= Floor(m_drectView.left / dMajorUnitX))
    	{ //in this case we are negative or right on a division
    		xstart = (Floor(m_drectView.left / dMajorUnitX)) * dMajorUnitX - dMajorUnitX;
    	}
    	else
    	{ //in this case we are positive
    		xstart = (Floor(m_drectView.left / dMajorUnitX)) * dMajorUnitX;
    	}

    	int pixelMajorUnitX = (int)(dMajorUnitX / m_dXMultiplier) + 1;
    	int iMajorUnitsPerLabelX = (int)(metricsXAxisDivision.stringWidth("8888.8") / pixelMajorUnitX) + 1;

    	// Run through all the major x divisions
    	for (i = 0; i < iNumXDivisions; i++)
    	{
    		int xpos = (int)(m_rectGraph.left + (int)(((xstart - m_drectView.left) + (dMajorUnitX * i)) * m_dInvXMultiplier));
  		
    		if (xpos <= m_rectGraph.right
    			&& xpos >= m_rectGraph.left)
    		{			
    			double dValue = xstart + dMajorUnitX * i;

    			double dBaseValue;
    			double dNextBaseValue;

    			if (m_iMajorUnitTypeX == 8)
    			{
    				dBaseValue = 0;
    				dNextBaseValue = 0;
    			}
    			else if (m_iMajorUnitTypeX == 7)
    			{
    				dBaseValue = (Floor(dValue / m_dNextMajorUnitXTypeValue)) * m_dNextMajorUnitXTypeValue;
    				dNextBaseValue = 0;
    			}
    			else
    			{
    				dBaseValue = (Floor(dValue / m_dNextMajorUnitXTypeValue)) * m_dNextMajorUnitXTypeValue;
    				dNextBaseValue = (Floor(dValue / m_dNextNextMajorUnitXTypeValue)) * m_dNextNextMajorUnitXTypeValue;
    			}

    			double dDisplayValue = (double)((dValue - dBaseValue) / m_dMajorUnitXTypeValue);
    			double dNextDisplayValue = (double)((dValue - dNextBaseValue) / m_dNextMajorUnitXTypeValue);

    			//Check to see if it is greater than a major graduation
    			if (dDisplayValue == 0)
    			{//Greater than a major graduation

    				//Draw the increment line
    		   		gl.glColor3f(51f/255f, 51f/255f, 51f/255f);
    				gl.glBegin(GL.GL_LINES);
    					gl.glVertex2i(xpos, (int)(m_rectGraph.bottom - GREAT_LINE_LENGTH));
    					gl.glVertex2i(xpos, (int)m_rectGraph.bottom);
    				gl.glEnd();
    									
    				//Draw the text underneath the increment graduations
    				//Find the highest unit that is at an increment point and use that
    				//Unless it's zero, in which case we will just use a ??
    				String str = decformat.format(dNextDisplayValue);
    				// Only draw the units if it's a value other than 0.
    				if (dValue != 0)
    					str += " " + strNextXAxisLabel;

    		        printGL(m_rendererXAxisDivision, xpos, (int)m_rectGraph.bottom - GREAT_LINE_LENGTH - metricsXAxisDivision.getAscent() - 2, JUSTIFY_CENTER, 0, str);
    		    }
    			else
    			{ // Just a major graduation
    				double x2 = (dValue / dMajorUnitX) / (double)iMajorUnitsPerLabelX;
    				double x3 = Floor(x2);

    				if (x2 == x3)
    				{
    					//Draw the text underneath the major graduations
        				String str = decformat.format(dDisplayValue);
        		        printGL(m_rendererXAxisDivision, xpos, (int)m_rectGraph.bottom - MAJOR_LINE_LENGTH - metricsXAxisDivision.getAscent() - 2, JUSTIFY_CENTER, 0, str);
    				}

    				//Draw the major line
    		   		gl.glColor3f(51f/255f, 51f/255f, 51f/255f);
    				gl.glBegin(GL.GL_LINES);
    					gl.glVertex2i(xpos, (int)(m_rectGraph.bottom - MAJOR_LINE_LENGTH));
    					gl.glVertex2i(xpos, (int)m_rectGraph.bottom);
    				gl.glEnd();
    			}

    			//Draw the vertical lines that form the grid of the graph
    			gl.glColor3f((float)220/(float)255,(float)220/(float)255,(float)220/(float)255); //Light grey
    			gl.glBegin(GL.GL_LINES);
    				gl.glVertex2i(xpos, (int)m_rectGraph.bottom);
    				gl.glVertex2i(xpos, (int)m_rectGraph.top);
    			gl.glEnd();
    		}

    		//Draw the minor graduations
       		gl.glColor3f(51f/255f, 51f/255f, 51f/255f);

    		for (j = 1; j < 5; j++)
    		{		
    			int extraxpos = (int)((dMajorUnitX * ((double)j / (double)5)) * m_dInvXMultiplier);
    			
    			if (xpos + extraxpos <= m_rectGraph.right
    				&& xpos + extraxpos >= m_rectGraph.left)
    			{
    				gl.glBegin(GL.GL_LINES);
    					gl.glVertex2i(xpos + extraxpos, (int)(m_rectGraph.bottom - MINOR_LINE_LENGTH));
    					gl.glVertex2i(xpos + extraxpos, (int)m_rectGraph.bottom);
    				gl.glEnd();
    			}
    		}
    	}
    	int leftvalue, rightvalue;

    	String strTopLeft;
    	String strTopRight;
    	String strBottomLeft;
    	String strBottomRight;

    	boolean leftneg = false;
    	boolean rightneg = false;

    	strTopLeft = "";
    	strTopRight = "";
    	strBottomLeft = "";
    	strBottomRight = "";

    	//Now decide which values should be shown
    	//nanoseconds = 1
    	//years = 8
    	switch(m_iMajorUnitTypeX)
    	{
    	case 1:
    		{
    			leftvalue = (int)((m_drectView.left - (Floor(m_drectView.left / MILLISECONDS) * MILLISECONDS)) / MICROSECONDS);
    			rightvalue = (int)((m_drectView.right - (Floor(m_drectView.right / MILLISECONDS) * MILLISECONDS)) / MICROSECONDS);
    			if (leftvalue < 0)
    			{
    				leftvalue = Math.abs(leftvalue);
    				leftneg = true;
    			}
    			if (rightvalue < 0)
    			{
    				rightvalue = Math.abs(rightvalue);
    				rightneg = true;
    			}
    			strBottomLeft = String.format(".%03d", leftvalue);
    			strBottomRight = String.format(".%03d", rightvalue);

    			// Fall through
    		}
    	case 2:
    		{
    			leftvalue = (int)((m_drectView.left - (Floor(m_drectView.left / SECONDS) * SECONDS)) / MILLISECONDS);
    			rightvalue = (int)((m_drectView.right - (Floor(m_drectView.right / SECONDS) * SECONDS)) / MILLISECONDS);
    			if (leftvalue < 0)
    			{
    				leftvalue = Math.abs(leftvalue);
    				leftneg = true;
    			}
    			if (rightvalue < 0)
    			{
    				rightvalue = Math.abs(rightvalue);
    				rightneg = true;
    			}
    			
    			strBottomLeft = String.format(".%03d", leftvalue) + strBottomLeft;
    			strBottomRight = String.format(".%03d", rightvalue) + strBottomRight;

    			// Fall through
    		}
    	case 3:
    		{
    			leftvalue = (int)((m_drectView.left - (Floor(m_drectView.left / MINUTES) * MINUTES)) / SECONDS);
    			rightvalue = (int)((m_drectView.right - (Floor(m_drectView.right / MINUTES) * MINUTES)) / SECONDS);
    			if (leftvalue < 0)
    			{
    				leftvalue = Math.abs(leftvalue);
    				leftneg = true;
    			}
    			if (rightvalue < 0)
    			{
    				rightvalue = Math.abs(rightvalue);
    				rightneg = true;
    			}
    			
    			strBottomLeft = String.format(":%02d", leftvalue) + strBottomLeft;
    			strBottomRight = String.format(":%02d", rightvalue) + strBottomRight;

    			// Fall through
    		}
    	case 4:
    		{
    			leftvalue = (int)((m_drectView.left - (Floor(m_drectView.left / HOURS) * HOURS)) / MINUTES);
    			rightvalue = (int)((m_drectView.right - (Floor(m_drectView.right / HOURS) * HOURS)) / MINUTES);
    			if (leftvalue < 0)
    			{
    				leftvalue = Math.abs(leftvalue);
    				leftneg = true;
    			}
    			if (rightvalue < 0)
    			{
    				rightvalue = Math.abs(rightvalue);
    				rightneg = true;
    			}
    			strBottomLeft = String.format(":%02d", leftvalue) + strBottomLeft;
    			strBottomRight = String.format(":%02d", rightvalue) + strBottomRight;
    			
    			// Fall through
    		}
    	case 5:
    		{
    			leftvalue = (int)((m_drectView.left - (Floor(m_drectView.left / DAYS) * DAYS)) / HOURS);
    			rightvalue = (int)((m_drectView.right - (Floor(m_drectView.right / DAYS) * DAYS)) / HOURS);
    			if (m_iMajorUnitTypeX == 5)
    			{
    				strBottomLeft = String.format("Hour %d", leftvalue);
    				strBottomRight = String.format("Hour %d", rightvalue);
    			}
    			else
    			{
    				if (leftvalue < 0)
    				{
    					leftvalue = Math.abs(leftvalue);
    					leftneg = true;
    				}
    				if (rightvalue < 0)
    				{
    					rightvalue = Math.abs(rightvalue);
    					rightneg = true;
    				}
    				strBottomLeft = String.format("%02d",leftvalue) + strBottomLeft;
    				strBottomRight = String.format("%02d", rightvalue) + strBottomRight;
    				if (leftneg)
    					strBottomLeft = "-" + strBottomLeft;
    				if (rightneg)
    					strBottomRight = "-" + strBottomRight;
    			}
    			
    			// Fall through
    		}
    	case 6:
    		{
    			leftvalue = (int)((m_drectView.left - (Floor(m_drectView.left / YEARS) * YEARS)) / DAYS);
    			rightvalue = (int)((m_drectView.right - (Floor(m_drectView.right / YEARS) * YEARS)) / DAYS);
    			strTopLeft = String.format("Day %d", leftvalue);
    			strTopRight = String.format("Day %d", rightvalue);
    			
    			// Fall through
    		}
    	case 7:
    		{
    			leftvalue = (int)Floor(m_drectView.left / YEARS);
    			rightvalue = (int)Floor(m_drectView.right / YEARS);
    			if (m_iMajorUnitTypeX == 7)
    			{
    				//strTopLeft = String.format("Year %d", leftvalue);
    				//strTopRight = String.format("Year %d", rightvalue);
    				strTopLeft = "";
    				strTopRight = "";
    			}
    			else
    			{
    				//strTopLeft += String.format(", Year %d", leftvalue);
    				//strTopRight += String.format(", Year %d",rightvalue);
    			}
    		}

    	}

        printGL(m_rendererXAxisDivision, (int)m_rectGraph.left, (int)(rectWindow.bottom + metricsXAxisDivision.getHeight() + 2), JUSTIFY_LEFT, 0, strTopLeft);
        printGL(m_rendererXAxisDivision, (int)m_rectGraph.left, (int)(rectWindow.bottom + 2), JUSTIFY_LEFT, 0, strBottomLeft);
        printGL(m_rendererXAxisDivision, (int)m_rectGraph.right, (int)(rectWindow.bottom + metricsXAxisDivision.getHeight() + 2), JUSTIFY_RIGHT, 0, strTopRight);
        printGL(m_rendererXAxisDivision, (int)m_rectGraph.right, (int)(rectWindow.bottom + 2), JUSTIFY_RIGHT, 0, strBottomRight);
        /**************Finished drawing the X-Axis***************/
        
        /**************Begin drawing the Y-Axis*****************/
    	double dMajorUnitY;

    	double dFrameRefY = 20 * m_dYMultiplier;

    	double dDifferenceY = m_drectView.top - m_drectView.bottom;

    	//Start if's from the bottom up - we want the smallest unit that fits
    	if (dFrameRefY <= 50 * NANOUNITS)
    	{	//Check nanounits
    		if (1 >= dFrameRefY)
    			dMajorUnitY = 1; // 1 nu
    		else if (2 >= dFrameRefY)
    			dMajorUnitY = 2; // 2 nu
    		else if (5 >= dFrameRefY)
    			dMajorUnitY = 5; // 5 nu
    		else if (10 >= dFrameRefY)
    			dMajorUnitY = 10; // 10 nu
    		else if (20 >= dFrameRefY)
    			dMajorUnitY = 20; // 20 nu
    		else
    			dMajorUnitY = 50; // 50 nu

    		m_iMajorUnitTypeY = 1;
    		m_dMajorUnitYTypeValue = NANOUNITS;
    		m_dNextMajorUnitYTypeValue = MICROUNITS;
    		m_dNextNextMajorUnitYTypeValue = MILLIUNITS;
    		m_strYAxisLabel = "nanounits";
    		m_strYAxisLabelShort = "nU";
    		strNextYAxisLabel = "\u03BCU";
    	}
    	else if (dFrameRefY <= 50 * MICROUNITS)
    	{	//Check microseconds
    		if (0.1 * MICROUNITS >= dFrameRefY)
    			dMajorUnitY = 0.1 * MICROUNITS; //0.1 us
    		else if (0.2 * MICROUNITS >= dFrameRefY)
    			dMajorUnitY = 0.2 * MICROUNITS; //0.2 us
    		else if (0.5 * MICROUNITS >= dFrameRefY)
    			dMajorUnitY = 0.5 * MICROUNITS; //0.5 us
    		else if (1 * MICROUNITS >= dFrameRefY)
    			dMajorUnitY = 1 * MICROUNITS; //1 us
    		else if (2 * MICROUNITS >= dFrameRefY)
    			dMajorUnitY = 2 * MICROUNITS; //2 us
    		else if (5 * MICROUNITS >= dFrameRefY)
    			dMajorUnitY = 5 * MICROUNITS; //5 us
    		else if (10 * MICROUNITS >= dFrameRefY)
    			dMajorUnitY = 10 * MICROUNITS; //10 us
    		else if (20 * MICROUNITS >= dFrameRefY)
    			dMajorUnitY = 20 * MICROUNITS; //20 us
    		else
    			dMajorUnitY = 50 * MICROUNITS; //50 us

    		m_iMajorUnitTypeY = 2;
    		m_dMajorUnitYTypeValue = MICROUNITS;
    		m_dNextMajorUnitYTypeValue = MILLIUNITS;
    		m_dNextNextMajorUnitYTypeValue = UNITS;
    		m_strYAxisLabel = "microunits";
    		m_strYAxisLabelShort = "\u03BCU";
    		strNextYAxisLabel = "mU";
    	}
    	else if (dFrameRefY <= 50 * MILLIUNITS) //Is the 11 pixel graduation less than 0.1 s?
    	{	//Check milliseconds
    		if (0.1 * MILLIUNITS >= dFrameRefY)
    			dMajorUnitY = 0.1 * MILLIUNITS; //0.1 ms
    		else if (0.2 * MILLIUNITS >= dFrameRefY)
    			dMajorUnitY = 0.2 * MILLIUNITS; //0.2 ms
    		else if (0.5 * MILLIUNITS >= dFrameRefY)
    			dMajorUnitY = 0.5 * MILLIUNITS; //0.5 ms
    		else if (1 * MILLIUNITS >= dFrameRefY)
    			dMajorUnitY = 1 * MILLIUNITS; //1 ms
    		else if (2 * MILLIUNITS >= dFrameRefY)
    			dMajorUnitY = 2 * MILLIUNITS; //2 ms
    		else if (5 * MILLIUNITS >= dFrameRefY)
    			dMajorUnitY = 5 * MILLIUNITS; //5 ms
    		else if (10 * MILLIUNITS >= dFrameRefY)
    			dMajorUnitY = 10 * MILLIUNITS; //10 ms
    		else if (20 * MILLIUNITS >= dFrameRefY)
    			dMajorUnitY = 20 * MILLIUNITS; //20 ms
    		else
    			dMajorUnitY = 50 * MILLIUNITS; //50 ms

    		m_iMajorUnitTypeY = 3;
    		m_dMajorUnitYTypeValue = MILLIUNITS;
    		m_dNextMajorUnitYTypeValue = UNITS;
    		m_dNextNextMajorUnitYTypeValue = KILOUNITS;
    		m_strYAxisLabel = "milliunits";
    		m_strYAxisLabelShort = "mU";
    		strNextYAxisLabel = "U";
    	}
    	else if (dFrameRefY <= 50 * UNITS) //Is the 11 pixel graduation less than 0.1 s?
    	{	//Check milliseconds
    		if (0.1 * UNITS >= dFrameRefY)
    			dMajorUnitY = 0.1 * UNITS; //0.1 ms
    		else if (0.2 * UNITS >= dFrameRefY)
    			dMajorUnitY = 0.2 * UNITS; //0.2 ms
    		else if (0.5 * UNITS >= dFrameRefY)
    			dMajorUnitY = 0.5 * UNITS; //0.5 ms
    		else if (1 * UNITS >= dFrameRefY)
    			dMajorUnitY = 1 * UNITS; //1 ms
    		else if (2 * UNITS >= dFrameRefY)
    			dMajorUnitY = 2 * UNITS; //2 ms
    		else if (5 * UNITS >= dFrameRefY)
    			dMajorUnitY = 5 * UNITS; //5 ms
    		else if (10 * UNITS >= dFrameRefY)
    			dMajorUnitY = 10 * UNITS; //10 ms
    		else if (20 * UNITS >= dFrameRefY)
    			dMajorUnitY = 20 * UNITS; //20 ms
    		else
    			dMajorUnitY = 50 * UNITS; //50 ms

    		m_iMajorUnitTypeY = 4;
    		m_dMajorUnitYTypeValue = UNITS;
    		m_dNextMajorUnitYTypeValue = KILOUNITS;
    		m_dNextNextMajorUnitYTypeValue = MEGAUNITS;
    		m_strYAxisLabel = "units";
    		m_strYAxisLabelShort = "U";
    		strNextYAxisLabel = "kU";
    	}
    	else if (dFrameRefY <= 50 * KILOUNITS) //Is the 11 pixel graduation less than 0.1 s?
    	{	//Check milliseconds
    		if (0.1 * KILOUNITS >= dFrameRefY)
    			dMajorUnitY = 0.1 * KILOUNITS; //0.1 ms
    		else if (0.2 * KILOUNITS >= dFrameRefY)
    			dMajorUnitY = 0.2 * KILOUNITS; //0.2 ms
    		else if (0.5 * KILOUNITS >= dFrameRefY)
    			dMajorUnitY = 0.5 * KILOUNITS; //0.5 ms
    		else if (1 * KILOUNITS >= dFrameRefY)
    			dMajorUnitY = 1 * KILOUNITS; //1 ms
    		else if (2 * KILOUNITS >= dFrameRefY)
    			dMajorUnitY = 2 * KILOUNITS; //2 ms
    		else if (5 * KILOUNITS >= dFrameRefY)
    			dMajorUnitY = 5 * KILOUNITS; //5 ms
    		else if (10 * KILOUNITS >= dFrameRefY)
    			dMajorUnitY = 10 * KILOUNITS; //10 ms
    		else if (20 * KILOUNITS >= dFrameRefY)
    			dMajorUnitY = 20 * KILOUNITS; //20 ms
    		else
    			dMajorUnitY = 50 * KILOUNITS; //50 ms

    		m_iMajorUnitTypeY = 5;
    		m_dMajorUnitYTypeValue = KILOUNITS;
    		m_dNextMajorUnitYTypeValue = MEGAUNITS;
    		m_strYAxisLabel = "kilounits";
    		m_strYAxisLabelShort = "kU";
    		strNextYAxisLabel = "MU";
    	}
    	else
    	{
    		if (0.1 * MEGAUNITS >= dFrameRefY)
    			dMajorUnitY = 0.1 * MEGAUNITS; //0.1 ms
    		else if (0.2 * MEGAUNITS >= dFrameRefY)
    			dMajorUnitY = 0.2 * MEGAUNITS; //0.2 ms
    		else if (0.5 * MEGAUNITS >= dFrameRefY)
    			dMajorUnitY = 0.5 * MEGAUNITS; //0.5 ms
    		else if (1 * MEGAUNITS >= dFrameRefY)
    			dMajorUnitY = 1 * MEGAUNITS; //1 ms
    		else if (2 * MEGAUNITS >= dFrameRefY)
    			dMajorUnitY = 2 * MEGAUNITS; //2 ms
    		else if (5 * MEGAUNITS >= dFrameRefY)
    			dMajorUnitY = 5 * MEGAUNITS; //5 ms
    		else if (10 * MEGAUNITS >= dFrameRefY)
    			dMajorUnitY = 10 * MEGAUNITS; //10 ms
    		else if (20 * MEGAUNITS >= dFrameRefY)
    			dMajorUnitY = 20 * MEGAUNITS; //20 ms
    		else
    			dMajorUnitY = 50 * MEGAUNITS; //50 ms

    		m_iMajorUnitTypeY = 6;
    		m_dMajorUnitYTypeValue = MEGAUNITS;
    		m_strYAxisLabelShort = "MU";
    		m_strYAxisLabel = "megaunits";
    	}
    	
    	//Now we have the major unit in ldMajorUnitY.

    	double ystart;

    	int iNumYDivisions = (int)(dDifferenceY / dMajorUnitY) + 2;

    	//Find the bottom start point
    	if ((m_drectView.bottom / dMajorUnitY) <= Floor(m_drectView.bottom / dMajorUnitY))
    	{ //in this case we are negative or right on a division
    		ystart = (Floor(m_drectView.bottom / dMajorUnitY)) * dMajorUnitY - dMajorUnitY;
    	}
    	else
    	{ //in this case we are positive
    		ystart = (Floor(m_drectView.bottom / dMajorUnitY)) * dMajorUnitY;
    	}

    	int pixelMajorUnitY = (int)(dMajorUnitY / m_dYMultiplier) + 1;
    	int iMajorUnitsPerLabelY = (int)(metricsYAxisDivision.stringWidth("8888.8") / pixelMajorUnitY) + 1;

    	for (i = 0; i < iNumYDivisions; i++)
    	{
    		int ypos = (int)m_rectGraph.bottom + (int)(((ystart - m_drectView.bottom) + (dMajorUnitY * (double)i)) * m_dInvYMultiplier);
    		
    		if (ypos >= m_rectGraph.bottom
    			&& ypos <= m_rectGraph.top)
    		{
    			double dValue = ystart + dMajorUnitY * (double)i; //ldValue is the CLongDouble value of the current major unit line

    			double dBaseValue; //ldBaseValue is the CLongDouble value of the current increment line unit
    			double dNextBaseValue; //ldNextBaseValue is the CLongDouble value of the current line unit past the ldBaseValue unit

    			if (m_iMajorUnitTypeY == 6)
    			{
    				dBaseValue = 0;
    				dNextBaseValue = 0;
    			}
    			else if (m_iMajorUnitTypeY == 5)
    			{
    				dBaseValue = (Floor(dValue / m_dNextMajorUnitYTypeValue)) * m_dNextMajorUnitYTypeValue;
    				dNextBaseValue = 0;
    			}
    			else
    			{
    				dBaseValue = (Floor(dValue / m_dNextMajorUnitYTypeValue)) * m_dNextMajorUnitYTypeValue;
    				dNextBaseValue = (Floor(dValue / m_dNextNextMajorUnitYTypeValue)) * m_dNextNextMajorUnitYTypeValue;
    			}

    			double dDisplayValue = (double)((dValue - dBaseValue) / m_dMajorUnitYTypeValue);
    			double dNextDisplayValue = (double)((dValue - dNextBaseValue) / m_dNextMajorUnitYTypeValue);

    			//Check to see if it is greater than a major graduation
    			if (dDisplayValue == 0)
    			{//Greater than a major graduation

    				//Draw the increment line
    		   		gl.glColor3f(51f/255f, 51f/255f, 51f/255f);
    				gl.glBegin(GL.GL_LINES);
    					gl.glVertex2i((int)(m_rectGraph.left - GREAT_LINE_LENGTH), ypos);
    					gl.glVertex2i((int)m_rectGraph.left, ypos);
    				gl.glEnd();
    					
    				//Draw the text to the left of the increment graduations
    				String str = decformat.format(dNextDisplayValue);

    				// Only draw the units if it's a value other than 0.
    				if (dValue != 0)
    					str += strNextYAxisLabel;

    		        printGL(m_rendererYAxisDivision, (int)(m_rectGraph.left - GREAT_LINE_LENGTH - 2), ypos, JUSTIFY_CENTER, 90, str);
    			}
    			else
    			{//Just a major graduation
    				double x2 = (dValue / dMajorUnitY) / (double)iMajorUnitsPerLabelY;
    				double x3 = Floor(x2);

    				if (x2 == x3)
    				{
    					//Draw the text underneath the major graduations
    					String str = decformat.format(dDisplayValue);
    										    					
        		        printGL(m_rendererYAxisDivision, (int)(m_rectGraph.left - MAJOR_LINE_LENGTH - 2), ypos, JUSTIFY_CENTER, 90, str);
    				}

    				//Draw the major line
    		   		gl.glColor3f(51f/255f, 51f/255f, 51f/255f);
    				gl.glBegin(GL.GL_LINES);
    					gl.glVertex2i((int)(m_rectGraph.left - MAJOR_LINE_LENGTH), ypos);
    					gl.glVertex2i((int)m_rectGraph.left, ypos);
    				gl.glEnd();
    			}

    			//Draw the vertical lines that form the grid of the graph
    			gl.glColor3f((float)220/(float)255,(float)220/(float)255,(float)220/(float)255); //Light grey
    			gl.glBegin(GL.GL_LINES);
    				gl.glVertex2i((int)m_rectGraph.left, ypos);
    				gl.glVertex2i((int)m_rectGraph.right, ypos);
    			gl.glEnd();
    		}

    		//Draw the minor graduations
       		gl.glColor3f(51f/255f, 51f/255f, 51f/255f);

    		for (j = 1; j < 5; j++)
    		{
    			int extraypos = (int)((dMajorUnitY * (double)((double)j / (double)5)) * m_dInvYMultiplier);
    			if (ypos + extraypos >= m_rectGraph.bottom
    				&& ypos + extraypos <= m_rectGraph.top)
    			{
    				gl.glBegin(GL.GL_LINES);
    					gl.glVertex2i((int)(m_rectGraph.left - MINOR_LINE_LENGTH), ypos + extraypos);
    					gl.glVertex2i((int)m_rectGraph.left, ypos + extraypos);
    				gl.glEnd();
    			}
    		}
    	}

    	int topvalue, bottomvalue;
    	boolean topneg = false;
    	boolean bottomneg = false;

    	String strTop;
    	String strBottom;
    	
    	strTop = "";
    	strBottom = "";
    	//Now decide which values should be shown
    	//fV = 1
    	//MV = 8
    	switch(m_iMajorUnitTypeY)
    	{
    	case 1:
    		{
    			topvalue = (int)((m_drectView.top - (Floor(m_drectView.top / MILLIUNITS) * MILLIUNITS)) / MICROUNITS);
    			bottomvalue = (int)((m_drectView.bottom - (Floor(m_drectView.bottom / MILLIUNITS) * MILLIUNITS)) / MICROUNITS);
    			if (topvalue < 0)
    			{
    				topvalue = Math.abs(topvalue);
    				topneg = true;
    			}
    			if (bottomvalue < 0)
    			{
    				bottomvalue = Math.abs(bottomvalue);
    				bottomneg = true;
    			}
    			
    			strTop = String.format(" %03d", topvalue) + strTop;
    			strBottom = String.format(" %03d", bottomvalue) + strBottom;
    		}
    	case 2:
    		{
    			topvalue = (int)((m_drectView.top - (Floor(m_drectView.top / UNITS) * UNITS)) / MILLIUNITS);
    			bottomvalue = (int)((m_drectView.bottom - (Floor(m_drectView.bottom / UNITS) * UNITS)) / MILLIUNITS);
    			if (topvalue < 0)
    			{
    				topvalue = Math.abs(topvalue);
    				topneg = true;
    			}
    			if (bottomvalue < 0)
    			{
    				bottomvalue = Math.abs(bottomvalue);
    				bottomneg = true;
    			}
    			
    			strTop = String.format(".%03d", topvalue) + strTop;
    			strBottom = String.format(".%03d", bottomvalue) + strBottom;
    		}
    	case 3:
    		{
    			topvalue = (int)(Floor(m_drectView.top / UNITS));
    			bottomvalue = (int)(Floor(m_drectView.bottom / UNITS));
    			if (topvalue < 0)
    			{
    				topvalue = Math.abs(topvalue);
    				topneg = true;
    			}
    			if (bottomvalue < 0)
    			{
    				bottomvalue = Math.abs(bottomvalue);
    				bottomneg = true;
    			}

    			strTop = String.format("%d", topvalue) + strTop + " U";
    			strBottom = String.format("%d", bottomvalue) + strBottom + " U";

    			if (topneg)
    				strTop = "-" + strTop;
    			if (bottomneg)
    				strBottom = "-" + strBottom;

    			break;
    		}
    	case 4:
    		{ //if it gets here, it must actually equal the number - we're on volts, need to only see kV
    			topvalue = (int)(Floor(m_drectView.top / KILOUNITS));
    			bottomvalue = (int)(Floor(m_drectView.bottom / KILOUNITS));

    			strTop = String.format("%d", topvalue) + strTop + " kU";
    			strBottom = String.format("%d", bottomvalue) + strBottom + " kU";
    			break;
    		}
    	case 5:
    		{
    			topvalue = (int)(Floor(m_drectView.top / MEGAUNITS));
    			bottomvalue = (int)(Floor(m_drectView.bottom / MEGAUNITS));

    			strTop = String.format("%d", topvalue) + strTop + " MU";
    			strBottom = String.format("%d", bottomvalue) + strBottom + " MU";
    			break;
    		}

    	}
    	
        printGL(m_rendererYAxisDivision, (int)(rectWindow.left + 2 + metricsYAxisLabel.getHeight() + metricsYAxisDivision.getHeight()), (int)m_rectGraph.bottom, JUSTIFY_LEFT, 90, strBottom);
        printGL(m_rendererYAxisDivision, (int)(rectWindow.left + 2 + metricsYAxisLabel.getHeight() + metricsYAxisDivision.getHeight()), (int)m_rectGraph.top, JUSTIFY_RIGHT, 90, strTop);

    /**************Finished drawing the Y-Axis***************/

    	//Draw Axes
   		gl.glColor3f(51f/255f, 51f/255f, 51f/255f);
    	gl.glBegin(GL.GL_LINE_STRIP);
    		gl.glVertex2i((int)m_rectGraph.left, (int)m_rectGraph.top);
    		gl.glVertex2i((int)m_rectGraph.left, (int)m_rectGraph.bottom);
    		gl.glVertex2i((int)m_rectGraph.right, (int)m_rectGraph.bottom);
    	gl.glEnd();

    	String str;

    	//Draw the text on the left side
    	str = String.format("Signal (%s)", m_strYAxisLabel);
        printGL(m_rendererYAxisLabel, (int)(rectWindow.left + metricsYAxisLabel.getHeight()), (int)(m_rectGraph.bottom + (Math.abs(m_rectGraph.top - m_rectGraph.bottom) / 2)), JUSTIFY_CENTER, 90, str);
    	
    	//Draw the text on the bottom
    	str = String.format("Time (%s)", m_strXAxisLabel);
    	int iBottomOfXMarker = (int)(m_rectGraph.bottom - (metricsXAxisDivision.getHeight() + GREAT_LINE_LENGTH));
    	int iSpaceOnBottom = (int)(iBottomOfXMarker - rectWindow.bottom);
    	int iTextPos = (int)(m_rectGraph.bottom - (metricsXAxisDivision.getHeight() + GREAT_LINE_LENGTH) - ((iSpaceOnBottom / 2) + (metricsXAxisLabel.getHeight() / 2)));
        printGL(m_rendererXAxisLabel, (int)(m_rectGraph.left + ((m_rectGraph.right - m_rectGraph.left) / 2)), iTextPos, JUSTIFY_CENTER, 0, str);
    }

    public void DrawChannelLines()
    {
    	GL gl = this.getGL();

    	DPoint dLastPoint = new DPoint();
    	DPoint dCurrentPoint = new DPoint();
    	DPoint dLeftPoint;
    	DPoint dRightPoint;
    	
    	DPoint bdLastPoint;
    	DPoint bdCurrentPoint;

    	int x1 = 0;
    	int x2 = 0;
    	int y1 = 0;
    	int y2 = 0;
    	
    	double dSlope;
    	double dRightVal;
    	double dLeftVal;

    	int i = 0;
    	
    	for (i = 0; i < this.m_vectDataSeries.size(); i++)
    	{
    		//Set up the line
    		gl.glLineWidth((float)m_vectDataSeries.get(i).iLineThickness);
    		//Slows things WAY down to have the line stippling
    		//SetLineStyle(pChannel->m_iLineStyle, pChannel->m_fLineWidth);

    		float linercolor = (float)m_vectDataSeries.get(i).clrLineColor.getRed()/(float)255;
    		float linegcolor = (float)m_vectDataSeries.get(i).clrLineColor.getGreen()/(float)255;
    		float linebcolor = (float)m_vectDataSeries.get(i).clrLineColor.getBlue()/(float)255;
    		
    		int j = 0;
    		
			for (j = 0; j < m_vectDataSeries.get(i).vectDataArray.size(); j++)
			{
				if (j == 0)
				{
					bdLastPoint = m_vectDataSeries.get(i).vectDataArray.get(0);
					dLastPoint.x = m_rectGraph.left + (m_dInvXMultiplier * (bdLastPoint.x - m_drectView.left));
					dLastPoint.y = m_rectGraph.bottom + (m_dInvYMultiplier * (bdLastPoint.y - m_drectView.bottom));
				}
				else
				{

					bdCurrentPoint = m_vectDataSeries.get(i).vectDataArray.get(j);
					dCurrentPoint.x = m_rectGraph.left + (m_dInvXMultiplier * (bdCurrentPoint.x - m_drectView.left));
					dCurrentPoint.y = m_rectGraph.bottom + (m_dInvYMultiplier * (bdCurrentPoint.y - m_drectView.bottom));
					
					if (dCurrentPoint.x > dLastPoint.x)
					{
						dLeftPoint = dLastPoint;
						dRightPoint = dCurrentPoint;
					}
					else
					{
						dLeftPoint = dCurrentPoint;
						dRightPoint = dLastPoint;						
					}
					
					//Don't draw any lines where both points are outside the border lines
					if (!((dLeftPoint.x < m_rectGraph.left && dRightPoint.x < m_rectGraph.left) || 
						(dLeftPoint.x > m_rectGraph.right && dRightPoint.x > m_rectGraph.right) || 
						(dLeftPoint.y < m_rectGraph.bottom && dRightPoint.y < m_rectGraph.bottom) || 
						(dLeftPoint.y > m_rectGraph.top && dRightPoint.y > m_rectGraph.top)))
					{
						//where both points are inside the viewrect
						if ((dLeftPoint.x >= m_rectGraph.left && dRightPoint.x >= m_rectGraph.left) && 
							(dLeftPoint.x <= m_rectGraph.right && dRightPoint.x <= m_rectGraph.right) && 
							(dLeftPoint.y >= m_rectGraph.bottom && dRightPoint.y >= m_rectGraph.bottom) && 
							(dLeftPoint.y <= m_rectGraph.top && dRightPoint.y <= m_rectGraph.top))
						{
							x1 = (int)dLeftPoint.x; 
							y1 = (int)dLeftPoint.y; 
							x2 = (int)dRightPoint.x;
							y2 = (int)dRightPoint.y;
						}
						//where the first point is inside the viewrect, the second is not
						else if ((	dLeftPoint.x >= m_rectGraph.left && 
									dLeftPoint.x <= m_rectGraph.right && 
									dLeftPoint.y >= m_rectGraph.bottom && 
									dLeftPoint.y <= m_rectGraph.top) && 
									(dRightPoint.y < m_rectGraph.bottom || 
									dRightPoint.y > m_rectGraph.top || 
									dRightPoint.x < m_rectGraph.left || 
									dRightPoint.x > m_rectGraph.right))
						{
							dSlope = ((double)dRightPoint.y - (double)dLeftPoint.y)/((double)dRightPoint.x - (double)dLeftPoint.x);

							dRightVal = (dSlope * ((double)m_rectGraph.right - dLeftPoint.x)) + dLeftPoint.y;

							if (m_rectGraph.top < dRightVal)
							{
								x2 = (int)(((m_rectGraph.top - dLeftPoint.y) / dSlope) + dLeftPoint.x);
								y2 = (int)m_rectGraph.top;
							}
							else if (m_rectGraph.bottom > dRightVal)
							{
								x2 = (int)(((m_rectGraph.bottom - dLeftPoint.y) / dSlope) + dLeftPoint.x);
								y2 = (int)m_rectGraph.bottom;
							}
							else
							{
								x2 = (int)m_rectGraph.right;
								y2 = (int)dRightVal;
							}

							x1 = (int)dLeftPoint.x;
							y1 = (int)dLeftPoint.y; 
						}
						//where the second point is inside the viewrect, the first is not - works
						else if ((	dRightPoint.x >= m_rectGraph.left && 
									dRightPoint.x <= m_rectGraph.right && 
									dRightPoint.y >= m_rectGraph.bottom && 
									dRightPoint.y <= m_rectGraph.top) && 
									(dLeftPoint.y < m_rectGraph.bottom || 
									dLeftPoint.y > m_rectGraph.top || 
									dLeftPoint.x < m_rectGraph.left || 
									dLeftPoint.x > m_rectGraph.right))
						{
							dSlope = ((double)dRightPoint.y - (double)dLeftPoint.y)/((double)dRightPoint.x - (double)dLeftPoint.x);

							dLeftVal = (dSlope * (m_rectGraph.left - dRightPoint.x)) + dRightPoint.y;

							if (m_rectGraph.top < dLeftVal)
							{
								x1 = (int)(((m_rectGraph.top - dRightPoint.y) / dSlope) + dRightPoint.x);
								y1 = (int)m_rectGraph.top;
							}
							else if (m_rectGraph.bottom > dLeftVal)
							{
								x1 = (int)(((m_rectGraph.bottom - dRightPoint.y) / dSlope) + dRightPoint.x);
								y1 = (int)m_rectGraph.bottom;
							}
							else
							{
								x1 = (int)m_rectGraph.left;
								y1 = (int)dLeftVal;
							}

							x2 = (int)dRightPoint.x;
							y2 = (int)dRightPoint.y;
						}
						//where neither point is inside the viewrect
						else if ((	dRightPoint.x < m_rectGraph.left || 
									dRightPoint.x > m_rectGraph.right || 
									dRightPoint.y < m_rectGraph.bottom || 
									dRightPoint.y > m_rectGraph.top) && 
									(dLeftPoint.x < m_rectGraph.left || 
									dLeftPoint.x > m_rectGraph.right || 
									dLeftPoint.y < m_rectGraph.bottom || 
									dLeftPoint.y > m_rectGraph.top))
						{
							dSlope = ((double)dRightPoint.y - (double)dLeftPoint.y)/((double)dRightPoint.x - (double)dLeftPoint.x);

							//if the line goes off the right side of the graphrect
							//dRightVal contains the value of the point if it were at the right side of the graphrect
							dRightVal = (dSlope * (m_rectGraph.right - dRightPoint.x)) + dRightPoint.y;
							//lefttval contains the value of the point if it were at the left side of the graphrect
							dLeftVal = (dSlope * (m_rectGraph.left - dRightPoint.x)) + dRightPoint.y;
							//Is the right point off the top?
							if (m_rectGraph.top < dRightVal)
							{
								x2 = (int)(((m_rectGraph.top - dRightPoint.y) / dSlope) + dRightPoint.x);
								y2 = (int)m_rectGraph.top;
							}
							//Is the right point off the bottom
							else if (m_rectGraph.bottom > dRightVal)
							{
								x2 = (int)(((m_rectGraph.bottom - dRightPoint.y) / dSlope) + dRightPoint.x);
								y2 = (int)m_rectGraph.bottom;
							}
							//The right point must be off the right side
							else
							{
								x2 = (int)m_rectGraph.right;
								y2 = (int)dRightVal;
							}

							//Is the left point off the top?
							if (m_rectGraph.top < dLeftVal)
							{
								x1 = (int)(((m_rectGraph.top - dRightPoint.y) / dSlope) + dRightPoint.x);
								y1 = (int)m_rectGraph.top;
							}
							//Is the left point off the bottom?
							else if (m_rectGraph.bottom > dLeftVal)
							{
								x1 = (int)(((m_rectGraph.bottom - dRightPoint.y) / dSlope) + dRightPoint.x);
								y1 = (int)m_rectGraph.bottom;
							}
							//The left point must be off the left side
							else
							{
								x1 = (int)m_rectGraph.left;
								y1 = (int)dLeftVal;
							}
						}

						gl.glColor3f(linercolor, linegcolor, linebcolor);
						gl.glBegin(GL.GL_LINES);
							gl.glVertex2i(x1, y1);
							gl.glVertex2i(x2, y2);
						gl.glEnd();
					}
					
					bdLastPoint = bdCurrentPoint;
					dLastPoint.x = dCurrentPoint.x;
					dLastPoint.y = dCurrentPoint.y;
				}
			}
			
//    		glDisable(GL_LINE_STIPPLE);
    	}

    	gl.glLineWidth(1.0f); //Return the line width back to 1;
    }
    
    private void DrawZoomBox()
    {
    	GL gl = this.getGL();
    	
    	if (m_iMode != 1 && m_iMode != 2)
    		return;

    	if (m_bZoomToolTracking == true)
    	{
    	//	glEnable(GL_LINE_STIPPLE);
    	//	glLineStipple(1,(WORD)0x5555);
    		gl.glColor3f(0.3f, 0.3f, 0.3f);
    		gl.glBegin(GL.GL_LINE_LOOP);
    			gl.glVertex2d(m_ZoomSelRect.left, m_ZoomSelRect.top);
    			gl.glVertex2d(m_ZoomSelRect.right, m_ZoomSelRect.top);
    			gl.glVertex2d(m_ZoomSelRect.right, m_ZoomSelRect.bottom);
    			gl.glVertex2d(m_ZoomSelRect.left, m_ZoomSelRect.bottom);
    		gl.glEnd();
    	//	glDisable(GL_LINE_STIPPLE);
    	}

    }
    private void printGL(TextRenderer renderer, int x, int y, int iJustification, float fAngle, String str)
    {
    	GL gl = this.getGL();

    	Graphics graphics = this.getGraphics();
    	FontMetrics metrics;
    	metrics = graphics.getFontMetrics(renderer.getFont());

        renderer.beginRendering(this.getWidth(), this.getHeight());
        gl.glMatrixMode(GL.GL_MODELVIEW);

    	renderer.setColor(51f/255f, 51f/255f, 51f/255f, 1.0f);

    	gl.glLoadIdentity();
    	gl.glTranslatef((float)x, (float)y, 0f);
        gl.glRotatef(fAngle, 0, 0, 1.0f);
    	
    	if (iJustification == JUSTIFY_CENTER)
    	{
            gl.glTranslatef((float)(0 - (metrics.stringWidth(str) / 2)), 0, 0);
    	}
    	else if (iJustification == JUSTIFY_RIGHT)
    	{
            gl.glTranslatef((float)(0 - metrics.stringWidth(str)), 0, 0);
    	}
    	
		renderer.draw(str, 0, 0);

    	renderer.endRendering();
    }

    private double Floor(double dNumber)
    {
 	   if (dNumber > 0)
 		   return Math.floor(dNumber);
 	   else if (dNumber < 0)
 		   return Math.ceil(dNumber);
 	   else
 		   return 0;
    }

	//@Override
	public void mouseDragged(MouseEvent arg0) 
	{
	}
	
	//@Override
	public void mouseMoved(MouseEvent arg0) 
	{
	}
	
	//@Override
	public void mouseClicked(MouseEvent arg0) 
	{
	}
	
	//@Override
	public void mouseEntered(MouseEvent arg0) 
	{
	}
	
	//@Override
	public void mouseExited(MouseEvent arg0) 
	{
	}
	
	//@Override
	public void mousePressed(MouseEvent arg0) 
	{
		if (m_iMode == 0)
		{
			setCursor(this.m_curClosedHand);
		}		
	}
	
	//@Override
	public void mouseReleased(MouseEvent arg0) 
	{
		if (m_iMode == 0)
		{
			setCursor(this.m_curOpenHand);
		}
	}

	public int AddSeries(String strSeriesName, Color clrLineColor, int iLineThickness)
	{
		DataSeries dataSeries = new DataSeries();
		
		dataSeries.strName = strSeriesName;
		dataSeries.clrLineColor = clrLineColor;
		dataSeries.iLineThickness = iLineThickness;
		
		m_vectDataSeries.add(dataSeries);
		
		return m_vectDataSeries.size() - 1;
	}
	
	public boolean RemoveSeries(int iSeriesIndex)
	{
		if (iSeriesIndex >= m_vectDataSeries.size())
			return false;
		
		m_vectDataSeries.remove(iSeriesIndex);
		
		return true;
	}
	
	public void RemoveAllSeries()
	{
		m_vectDataSeries.removeAllElements();			
		
		return;
	}

	public boolean AddDataPoint(int iSeriesIndex, double dXVal, double dYVal)
	{
		DataSeries dataSeries = null;
		
		if (iSeriesIndex >= m_vectDataSeries.size())
			return false;
		
		dataSeries = m_vectDataSeries.get(iSeriesIndex);
		
		if (dataSeries == null)
			return false;
		
		DPoint dPoint = new DPoint();
		dPoint.x = dXVal * SECONDS;
		dPoint.y = dYVal * UNITS;
		
		if (dataSeries.vectDataArray.size() == 0)
		{
			dataSeries.dXMax = dPoint.x;
			dataSeries.dXMin = dPoint.x;
			dataSeries.dYMax = dPoint.y;
			dataSeries.dYMin = dPoint.y;
		}
		else
		{
			if (dPoint.x > dataSeries.dXMax)
				dataSeries.dXMax = dPoint.x;
			if (dPoint.x < dataSeries.dXMin)
				dataSeries.dXMin = dPoint.x;
			if (dPoint.y > dataSeries.dYMax)
				dataSeries.dYMax = dPoint.y;
			if (dPoint.y < dataSeries.dYMin)
				dataSeries.dYMin = dPoint.y;
		}
		
		dataSeries.vectDataArray.add(dPoint);
		
		return true;
	}

	public boolean AutoScaleToSeries(int iSeriesIndex)
	{
		DataSeries dataSeries = null;
		
		if (iSeriesIndex >= m_vectDataSeries.size())
			return false;
		
		dataSeries = m_vectDataSeries.get(iSeriesIndex);
		
		if (dataSeries == null)
			return false;
		
		if (dataSeries.vectDataArray.size() < 2)
			return false;
		
		//this.m_drectView.bottom = dataSeries.dYMin;
		this.m_drectView.bottom = 0;
		this.m_drectView.top = dataSeries.dYMax;
		this.m_drectView.right = dataSeries.dXMax;
		this.m_drectView.left = dataSeries.dXMin;
		
		return true;
	}

	public boolean AutoScaleX()
	{
		double dXMin;
		double dXMax;
		
		if (m_vectDataSeries.size() == 0)
			return false;
		
		DataSeries dataSeries = null;
		dataSeries = m_vectDataSeries.get(0);
		
		dXMin = dataSeries.dXMin;
		dXMax = dataSeries.dXMax;
		
		for (int i = 1; i < m_vectDataSeries.size(); i++)
		{
			dataSeries = m_vectDataSeries.get(i);
			
			if (dataSeries.dXMin < dXMin)
				dXMin = dataSeries.dXMin;
			if (dataSeries.dXMax > dXMax)
				dXMax = dataSeries.dXMax;
		}
		
		this.m_drectView.right = dataSeries.dXMax;
		this.m_drectView.left = dataSeries.dXMin;
		
		return true;
	}

	public boolean AutoScaleY()
	{
		double dYMin;
		double dYMax;
		
		if (m_vectDataSeries.size() == 0)
			return false;
		
		DataSeries dataSeries = null;
		dataSeries = m_vectDataSeries.get(0);
		
		dYMin = dataSeries.dYMin;
		dYMax = dataSeries.dYMax;
		
		for (int i = 1; i < m_vectDataSeries.size(); i++)
		{
			dataSeries = m_vectDataSeries.get(i);
			
			if (dataSeries.dYMin < dYMin)
				dYMin = dataSeries.dYMin;
			if (dataSeries.dYMax > dYMax)
				dYMax = dataSeries.dYMax;
		}
		
		this.m_drectView.bottom = 0;
		this.m_drectView.top = dataSeries.dYMax;
		
		return true;
	}

	void selectPanMode()
	{
		m_iMode = 0;
        setCursor(m_curOpenHand);
	}
	
	void selectZoomInMode()
	{
		m_iMode = 1;
        setCursor(m_curZoomIn);
	}
	
	void selectZoomOutMode()
	{
		m_iMode = 2;
        setCursor(m_curZoomOut);
	}
	
	void setAutoScaleX(boolean bAutoScaleX)
	{
		this.m_bAutoScaleX = bAutoScaleX;
		if (bAutoScaleX == true)
		{
			this.AutoScaleX();
		}
		fireAutoScaleChangedEvent();
	}
	
	void setAutoScaleY(boolean bAutoScaleY)
	{
		this.m_bAutoScaleY = bAutoScaleY;
		if (bAutoScaleY == true)
		{
			this.AutoScaleY();
		}
		fireAutoScaleChangedEvent();
	}
	
	boolean getAutoScaleX()
	{
		return m_bAutoScaleX;
	}

	boolean getAutoScaleY()
	{
		return m_bAutoScaleY;
	}
	
    public synchronized void addAutoScaleListener(AutoScaleListener l) 
    {
        _listeners.add(l);
    }
    
    public synchronized void removeMoodListener(AutoScaleListener l)
    {
        _listeners.remove(l);
    }
     
    private synchronized void fireAutoScaleChangedEvent() 
    {
        AutoScaleEvent autoScaleEvent = new AutoScaleEvent(this, this.m_bAutoScaleX, this.m_bAutoScaleY);
        Iterator<AutoScaleListener> listeners = _listeners.iterator();
        while(listeners.hasNext()) 
        {
            ((AutoScaleListener)listeners.next()).autoScaleChanged(autoScaleEvent);
        }
    }
}