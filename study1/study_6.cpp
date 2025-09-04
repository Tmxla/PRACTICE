#include "iostream"
#include "string"
using namespace std;

int main() {
    int A, B;
    cin >> A >> B;
    if (A > 0 && B < 10){
        cout << A * B << endl;
    }
    else {
        cout << "A는 양수, B는 10보다 작아야 합니다." << endl;
    }
    return 0;
}