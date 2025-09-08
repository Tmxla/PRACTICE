#include <iostream>
using namespace std;

int main() {
    int X, N, a, b;
    int sum = 0;
    cin >> X;
    cin >> N;
    for (int i = 1; i <= N; i++) {
        cin >> a >> b;
        for (int j = 1; j <= 1; j++) {
            sum += a * b;
        }
    }
    if (sum == X)
        cout << "Yes" << endl;
    else
        cout << "No" << endl;
}