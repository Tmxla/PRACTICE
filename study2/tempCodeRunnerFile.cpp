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
        if(A>=23) {
            A=0;
            cout << A+(B+C)/60 << " " << (B+C)-60 << endl;
        }
        else
            cout << A+(B+C)/60 << " " << (B+C)-60 << endl;
    }
    return 0;
}