#include <iostream>
using namespace std;

int main() {
    int n, sum;
    cin >> n;
    for (int i = 1; i <= n; i++) {
        sum = i;
        for (int j = 1; j < i; j++) {
            sum += j;
        }
    }
    cout << sum << endl;
}