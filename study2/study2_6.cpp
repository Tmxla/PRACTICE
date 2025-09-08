#include <iostream>
using namespace std;

int main() {
    int A, B, C;
    cin >> A >> B;
    cin >> C;

    if(B+C < 60) {
        cout << A << " " << B+C << endl;
    }
    else if(B+C >= 60) {
        A = A+(B+C)/60;
        B = (B+C)%60;
        A %= 24;
        cout << A << " " << B << endl;
    }
    return 0;
}