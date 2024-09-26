public class BF {

    public enum Ship{
        FOUR(4, "THREE_1"),
        THREE_1(3, "THREE_2"),
        THREE_2(3, "TWO_1"),
        TWO_1(2, "TWO_2"),
        TWO_2(2, "TWO_3"),
        TWO_3(2, "ONE_1"),
        ONE_1(1, "ONE_2"),
        ONE_2(1, "ONE_3"),
        ONE_3(1, "ONE_4"),
        ONE_4(1, "ZERO"),
        ZERO(0, "");
        private final int[][] cords;
        private final int size;
        private final String next;
        Ship(int size, String next){
            this.size = size;
            cords = new int[size][2];
            this.next = next;
        }
        public Ship getNext(){
            return Ship.valueOf(this.next);
        }
        public int getSize(){
            return this.size;
        }
        public void setCord(int i, int j, int index) {
            this.cords[index][0] = i;
            this.cords[index][1] = j;
        }
        public int getCordI(int index){
            return this.cords[index][0];
        }
        public int getCordJ(int index){
            return this.cords[index][1];
        }
        public static int totalSpots(){
            return 20;
        }
    }

    int[][] field;
    int[][] occupied;

    public BF(int[][] field) {
        this.field = field;
        this.occupied = new int[field.length][field[1].length];
    }

    public boolean validate() {
        return validateTotal() && validate(Ship.FOUR);
    }

    public boolean validateTotal(){
        int total = 0;
        for (int[] row : this.field) {
            for (int j = 0; j < this.field.length; j++) {
                total = row[j] == 1 ? total + 1 : total;
            }
        }
        return total == Ship.totalSpots();
    }

    public boolean validate(Ship ship){
        if(ship.name().equals("ZERO")){
            return true;
        }
        for (int i = 0; i < this.occupied.length; i++){
            for (int j = 0; j < this.occupied.length; j++){
                if (fitsH(i,j,ship)) {
                    occupy(ship);
                    if (validate(ship.getNext())){
                        return true;
                    }
                    free(ship);
                }
                if (fitsV(i,j,ship)) {
                    occupy(ship);
                    if (validate(ship.getNext())){
                        return true;
                    }
                    free(ship);
                }
            }
        }
        return false;
    }

    private void occupy(Ship ship) {
        for (int x = 0; x < ship.getSize(); x++) {
            this.occupied[ship.getCordI(x)][ship.getCordJ(x)] = 1;
        }
    }

    private void free(Ship ship) {
        for (int x = 0; x < ship.getSize(); x++) {
            this.occupied[ship.getCordI(x)][ship.getCordJ(x)] = 0;
        }
    }


    private boolean fitsH(int i, int j, Ship ship) {
        boolean fits = j + ship.getSize() <= 10 ;
        for (int x = 0; x < ship.getSize() && fits; x++){
            fits = (this.occupied[i][x + j] == 0) && (this.field[i][x + j] == 1);
            ship.setCord(i, j + x, x);
        }
        return fits;
    }

    private boolean fitsV(int i, int j, Ship ship) {
        boolean fits = i + ship.getSize() <= 10 ;
        for (int x = 0; x < ship.getSize() && fits; x++){
            fits = (this.occupied[x + i][j] == 0) && (this.field[x + i][j] == 1);
            ship.setCord(i + x, j, x);
        }
        return fits;
    }
}
