#include <iostream>
using namespace std;

int main() {
    int A, B, C;
    cin >> A >> B >> C;
    if(A==B && B==C)
        cout << 10000 + A*1000 << endl;
    else if(A==B || A==C)
        cout << 1000 + A*100 << endl;
    else if(B==C)
        cout << 1000 + B*100 << endl;
    else {
        if(A != B && A != C && B != C) {
            if(A>B && A>C)
                cout << A*100 << endl;
            else if(B>A && B>C)
                cout << B*100 << endl;
            else
                cout << C*100 << endl;
        }
        
    }
}