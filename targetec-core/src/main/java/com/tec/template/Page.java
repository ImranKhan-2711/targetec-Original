package com.tec.template;

import java.util.ArrayList;
import java.util.List;

public class Page<E>
{
  private int pageNumber;
  private int rowCount;
  private int pageSize;
  private int interval;
  private int pagesAvailable;
  private String sortIndex;
  private String sortOrder;
  private List<E> pageItems = new ArrayList();
  
  public Page(String sortIdx)
  {
    this.pageNumber = 1;
    this.pageSize = 100;
    this.sortOrder = "asc";
    this.sortIndex = sortIdx;
  }
  
  public Page(int pageNumber, int pageSize, int pagesAvailable, String sortIndex, String sortOrder)
  {
    this.pageNumber = pageNumber;
    this.pageSize = pageSize;
    this.pagesAvailable = pagesAvailable;
    this.sortIndex = sortIndex;
    this.sortOrder = sortOrder;
  }
  
  public void setPageNumber(int pageNumber)
  {
    this.pageNumber = pageNumber;
  }
  
  public void setPagesAvailable(int pagesAvailable)
  {
    this.pagesAvailable = pagesAvailable;
  }
  
  public void setPageItems(List<E> pageItems)
  {
    this.pageItems = pageItems;
  }
  
  public int getPageNumber()
  {
    return this.pageNumber;
  }
  
  public int getPageSize()
  {
    return this.pageSize;
  }
  
  public void setPageSize(int pageSize)
  {
    this.pageSize = pageSize;
  }
  
  public int getPagesAvailable()
  {
    return this.pagesAvailable;
  }
  
  public List<E> getPageItems()
  {
    return this.pageItems;
  }
  
  public String getSortIndex()
  {
    return this.sortIndex;
  }
  
  public void setSortIndex(String sortIndex)
  {
    this.sortIndex = sortIndex;
  }
  
  public String getSortOrder()
  {
    return this.sortOrder;
  }
  
  public void setSortOrder(String sortOrder)
  {
    this.sortOrder = sortOrder;
  }
  
  public int getRowCount()
  {
    return this.rowCount;
  }
  
  public void setRowCount(int rowCount)
  {
    this.rowCount = rowCount;
  }
  
  public int getInterval()
  {
    return this.interval;
  }
  
  public void setInterval(int interval)
  {
    this.interval = interval;
  }
  
  public void configurePage(int rowCount)
  {
    this.rowCount = rowCount;
    int pageCount = rowCount / this.pageSize;
    if (rowCount > this.pageSize * pageCount) {
      pageCount++;
    }
    this.pagesAvailable = pageCount;
  }
}
