package org.rogan.map.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class SpringBeanProxy
  implements BeanFactoryAware
{
  private BeanFactory beanFactory;

  public void setBeanFactory(BeanFactory beanFactory)
    throws BeansException
  {
    this.beanFactory = beanFactory;
  }

  public Object getBean(String name) {
    return this.beanFactory.getBean(name);
  }

  public <T> T getBean(Class<T> clz) {
    return this.beanFactory.getBean(clz);
  }

  public <T> T getBean(String name, Class<T> clz) {
    return this.beanFactory.getBean(name, clz);
  }

  public Object getBean(String name, Object[] args) {
    return this.beanFactory.getBean(name, args);
  }

  public <T> T getBean(Class<T> clz, Object[] args) {
    return this.beanFactory.getBean(clz, args);
  }

  public boolean contain(String name) {
    return this.beanFactory.containsBean(name);
  }
}