import "./globals.css";
import { ReactNode } from "react";
import { Provider } from "@/components/ui/provider";

export const metadata = {
  title: "Atendimento App",
  description: "Sistema de Gerenciamento de Atendimentos",
};

export default function RootLayout({ children }: { children: ReactNode }) {
  return (
    <html lang="pt" suppressHydrationWarning>
      <body>
        <Provider>{children}</Provider>
      </body>
    </html>
  );
}
