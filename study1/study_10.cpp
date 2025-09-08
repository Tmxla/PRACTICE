#include <iostream>
using namespace std;

int main() {
    int y;
    cin >> y;
    if (1000 <= y && y <= 3000) {
        y = y - 543;
    }
    cout << y << endl;
    return 0;
}