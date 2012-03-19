package org.retentionprediction;

import java.util.EventObject;

public class AutoScaleEvent extends EventObject
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean m_bAutoScaleXState;
	private boolean m_bAutoScaleYState;
	
	public AutoScaleEvent(Object source, boolean bAutoScaleXState, boolean bAutoScaleYState) 
	{
		super(source);
		m_bAutoScaleXState = bAutoScaleXState;
		m_bAutoScaleYState = bAutoScaleYState;
	}

    public boolean getAutoScaleXState() 
    {
        return m_bAutoScaleXState;
    }

    public boolean getAutoScaleYState() 
    {
        return m_bAutoScaleYState;
    }

}
