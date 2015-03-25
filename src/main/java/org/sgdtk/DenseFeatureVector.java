/*
 * Copyright (c) 2015 3CSI
 * All Rights Reserved
 *
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF 3CSI
 * The copyright notice above does not evidence any
 * actual or intended publication of such source code.
 */

package org.sgdtk;

import java.util.ArrayList;
import java.util.List;

public class DenseFeatureVector implements FeatureVector
{
    double[] x;
    double y;
    int length;
    
    public DenseFeatureVector()
    {
    }

    public DenseFeatureVector(double y)
    {
        this.y = y;
    }
    
    public DenseFeatureVector(double y, int length)
    {
        this.y = y;
        this.length = length;
        x = new double[length];
        
    }
    @Override
    public int length()
    {
        return length;
    }

    @Override
    public double getY()
    {
        return y;
    }

    @Override
    public void setY(double y)
    {
        this.y = y;
    }

    @Override
    public void add(Offset offset)
    {
        if (offset.index > length)
        {
            throw new RuntimeException("Index out of bounds! " + offset.index + ". Max is " + length);
        }
    }

    @Override
    public void update(double[] vec, double disp)
    {
        for (int i = 0; i < length; ++i)
        {
            vec[i] += x[i] * disp;
        }
    }

    @Override
    public double dot(double[] vec)
    {
        return CollectionsManip.dot(x, vec);
    }

    @Override
    public List<Offset> getNonZeroOffsets()
    {
        List<Offset> offsetList = new ArrayList<Offset>();
        for (int i = 0; i < length; ++i)
        {
            if (x[i] != 0.0)
            {
                offsetList.add(new Offset(i, x[i]));
            }
        }
        return offsetList;
    }

    @Override
    public void from(FeatureVector source)
    {
        this.y = source.getY();
        length = source.length();
        x = new double[length];
        
        for (Offset offset : source.getNonZeroOffsets())
        {
            x[offset.index] = offset.value;
        }
    }
}