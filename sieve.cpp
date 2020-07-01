int prime[1000009];
void sieve(int n)
{
    bool pr[n+1];
    for(int i = 1 ; i <= n ; i++)
        pr[i] = true;
    pr[1] = false;
    for(int i = 2 ; i <= sqrt(n) ; i++)
    {
        if(pr[i])
        {
            for(int j = i*i ; j <= n ; j += i)
            {
                pr[j] = false;
            }
        }
    }
    for(int i = 2 ; i <= n ; i++)
    {
        if(pr[i] == true)
        {
            prime[i]++;
        }
    }
    for(int i = 2 ; i <= n ; i++)
    {
        prime[i] += prime[i-1];
    }
}

// program for sieve
