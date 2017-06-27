#include <cassert>
#include <chrono>
#include <iostream>
using namespace std;

void row_major(long long n, int * & A, int * & B, int * & C)
{
     assert(A != 0);
     assert(B != 0);
     assert(C != 0);

     auto timea = chrono::steady_clock::now();
     for ( long long i = 0; i < n; ++i )
     {
          for ( long long j = 0; j < n; ++j )
          {
               C[i*n + j] = A[i*n + j] + B[i*n + j];
          }
     }
     auto timeb = chrono::steady_clock::now();
     auto time_taken = chrono::duration_cast<chrono::milliseconds>(timeb - timea).count();
     cout << (time_taken / 1000.0f);
}
void col_major(long long n, int * & A, int * & B, int * & C)
{
     assert(A != 0);
     assert(B != 0);
     assert(C != 0);

     auto timea = chrono::steady_clock::now();
     for ( long long j = 0; j < n; ++j )
     {
          for ( long long i = 0; i < n; ++i )
          {
               C[i*n + j] = A[i*n + j] + B[i*n + j];
          }
     }
     auto timeb = chrono::steady_clock::now();
     auto time_taken = chrono::duration_cast<chrono::milliseconds>(timeb - timea).count();
     cout << (time_taken / 1000.0f);
}

int main(int argc, char**argv)
{
     chrono::time_point<chrono::steady_clock> timea{};
     chrono::time_point<chrono::steady_clock> timeb{};
     long long time_taken{};

     // col-major for n=65536 takes a VERY long time

     cout << "n" << "\t" << "allocate" << "\t" << "row - major" << "\t" << "col - major" << "\t" << "deallocate" << endl;
     for ( long long
               n = 128;       // 128
               n <= 65536;    // 65536
               n *= 2 )
     {
          cout << n << "\t";

          // Allocate Arrays
          timea = chrono::steady_clock::now();
          int * A = new int[n*n]{};
          fill_n(A, n*n, 1);            // sets each element to 1
          int * B = new int[n*n]{};
          fill_n(B, n*n, 1);
          int * C = new int[n*n]{};
          fill_n(C, n*n, 1);
          timeb = chrono::steady_clock::now();
          time_taken = chrono::duration_cast<chrono::milliseconds>(timeb - timea).count();
          cout << (time_taken / 1000.0f);
          cout << "\t\t";

          // Run Algorithm
          row_major(n, A, B, C);
          cout << "\t\t";
          col_major(n, A, B, C);
          cout << "\t\t";

          // Deallocate Arrays
          timea = chrono::steady_clock::now();
          delete[] A;
          delete[] B;
          delete[] C;
          timeb = chrono::steady_clock::now();
          time_taken = chrono::duration_cast<chrono::milliseconds>(timeb - timea).count();
          cout << (time_taken / 1000.0f);

          cout << "\t\t" << "sec";
          cout << endl;
     }
     cout << endl << endl;

     system("pause");

     return 0;
}
