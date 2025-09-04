#include <iostream>
#include <string>
using namespace std;

int main() {
    cout << "Hello, World!/n" << endl;

    string name;
    int age = 0;
    cout << "이름과 나이를 입력하시오: ";
    cin >> name >> age;

    if (age < 19) {
        cout << "당신은 미자\n";
        cout << name;
    }
    else {
        cout << "당신은 성인\n";
        cout << name;
    }

    return 0;
}