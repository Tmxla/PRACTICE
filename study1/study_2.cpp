#include "iostream"
#include "string"
using namespace std;


int max(int a, int b, int c) {
    int maximum = (a > b) ? a : b;
    return (maximum > c) ? maximum : c;
}

int main() {
    int a, b, c;
    cout << "write three numbers: ";
    cin >> a >> b >> c;
    cout << max(a, b, c) << endl;
    return 0;
}