package org.example.dao;

public class ClueDaoMySql extends StoreElementDaoMySql implements ClueDao{
    private final ElementDao elementDao = new ElementDaoMySql();

    @Override
    public String createElementClue(){
        String query;
        int element_id = elementDao.inputElementTableInfo(3);
        if (element_id == -1){
            System.out.println("Failed to create element.");
            // Exception createElement
            query = null;
        } else {
            query = "INSERT INTO clue (element_id) " +
                    "VALUES (" + element_id + ");";
            storeElementInStorage(element_id);
        }
        return query;
    }
}
