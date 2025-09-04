#include <iostream>
using namespace std;

int main() {
    int a, b;
    cin >> a >> b;
    int b_1 = b % 10;
    int b_10 = (b/10) % 10;
    int b_100 = (b/100);
    cout << (a*b_1) << endl;
    cout << (a*b_10) << endl;
    cout << (a*b_100) << endl;
    cout << (a * b) << endl;

    return 0;
}