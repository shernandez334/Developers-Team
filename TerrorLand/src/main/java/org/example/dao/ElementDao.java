package org.example.dao;

public interface ElementDao {
    ElementEntity getElement(ElementEntity element);
    ElementEntity saveElement(ElementEntity element);
    ElementEntity deleteElement(ElementEntity element);
}
