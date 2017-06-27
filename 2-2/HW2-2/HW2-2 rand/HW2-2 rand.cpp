// Written by Eric in 2017

#include <iostream>
#include <windows.h>
#include <limits.h>
#include <ctime>
#include <cassert>
#include <chrono>
using namespace std;

void algorithm(long long m)
{
     chrono::time_point<chrono::steady_clock> timea{};
     chrono::time_point<chrono::steady_clock> timeb{};
     long long time_taken{};

     cout << "n" << "\t" << "allocate" << "\t" << "algorithm" << "\t" << "deallocate" << endl;
     for ( long long
               n = 16;       // 16
               n <= 16384;   // 16384
               n *= 4
         )
     {
          cout << n << "\t";

          // Allocate Arrays
          timea = chrono::steady_clock::now();
          int * A = new int[n*n]{};
          timeb = chrono::steady_clock::now();
          time_taken = chrono::duration_cast<chrono::milliseconds>(timeb - timea).count();
          cout << (time_taken / 1000.0f);
          cout << "\t\t";

          // Run Algorithm
          long long t = 0;    // current step
          int i = 0;          // row index
          int j = 0;          // col index
          int v = 0;          // value
          timea = chrono::steady_clock::now();
          while ( t < m )
          {
               i = rand() % (n);
               j = rand() % (n);
               v = rand();
               A[i*n + j] = v;
               ++t;
          }
          timeb = chrono::steady_clock::now();
          time_taken = chrono::duration_cast<chrono::milliseconds>(timeb - timea).count();
          cout << (time_taken / 1000.0f);
          cout << "\t\t";

          // Deallocate Arrays
          timea = chrono::steady_clock::now();
          delete[] A;
          timeb = chrono::steady_clock::now();
          time_taken = chrono::duration_cast<chrono::milliseconds>(timeb - timea).count();
          cout << (time_taken / 1000.0f);
          cout << "\t\t" << "sec";
          cout << endl;
     }
     cout << endl << endl;
}

int main()
{
     algorithm(1677721600); // 1,677,721,600
     algorithm(13421772800); // 13,421,772,800

     system("pause");

     return 0;
}
