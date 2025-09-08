#include <iostream>
using namespace std;

int main() {
    int H, M;
    cin >> H >> M;

    if(M>=45)
        cout << H << " " << M-45 << endl;
    if(M<45) {
        if (H==0)
        {
            cout << H+23 << " " << M+60-45 << endl;
        }
        else
            cout << H - 1 << " " << M+60-45 << endl;

    }
    return 0;
}