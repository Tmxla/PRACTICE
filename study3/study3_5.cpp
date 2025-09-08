#include <iostream>
using namespace std;

int main() {
    int N;
    cin >> N;
    int sum = N/4;
    for (int i = 1; i <= sum; i++) {
        cout << "long ";
    }
    cout << "int" << endl;
}