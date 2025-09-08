#include <iostream>
using namespace std;

int main() {
    int A = -10000 <= A;
    int B = B <= 10000;
    cin >> A >> B;
    switch (A > B)
    {
        case 1:
        cout << '>' << endl;
        break;
        case 0:
        switch (A < B)
        {
            case 1:
            cout << '<' << endl;
            break;
            case 0:
            cout << "==" << endl;
            break;    
        }
        break;
    }
}