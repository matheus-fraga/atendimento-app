// middleware.ts
// import { NextRequest, NextResponse } from 'next/server';

// export function middleware(req: NextRequest) {
//   // Captura o cabeçalho Cookie da requisição original
//   const cookie = req.headers.get('cookie') ?? '';

//   // Cria um novo conjunto de cabeçalhos para a requisição que será enviada ao backend
//   const headers = new Headers(req.headers);
//   headers.set('Cookie', cookie);

//   // Clona a URL da requisição original
//   const url = req.nextUrl.clone();

//   // Modifica o caminho da URL para indicar que deve ir para o backend
//   url.pathname = `/api${url.pathname}`;

//   // Reescreve a requisição para o novo destino com os cookies incluídos
//   return NextResponse.rewrite(url, { headers });
// }
