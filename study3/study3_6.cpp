#include <iostream>
using namespace std;

int main() {

    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    int A, B, T;
    cin >> T;
    for (int i = 1; i <=T; ++i) {
        cin >> A >> B;
        cout << A+B << "\n";
    }
}